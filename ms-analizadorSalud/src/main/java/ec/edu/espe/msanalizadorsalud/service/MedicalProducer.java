package ec.edu.espe.msanalizadorsalud.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import ec.edu.espe.msanalizadorsalud.DTO.NotificacionGeneralDTO;
import ec.edu.espe.msanalizadorsalud.DTO.NotificacionesDTO;
import ec.edu.espe.msanalizadorsalud.entity.MedicalAlert;
import ec.edu.espe.msanalizadorsalud.repository.MedicalAlertRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Slf4j
@Component
public class MedicalProducer {
    @Autowired
    private MedicalAlertRepository medicalAlertRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @RabbitListener(queues = "cola_microservicio")
    public void recibitMensajes(String mensaje){
        try{
            String id = "ALT-" + UUID.randomUUID().toString().substring(0, 8);
            log.info("Nuevo signo vital Analizando...");
            MedicalAlert medicalAlert = new MedicalAlert();
            NotificacionesDTO dto = objectMapper.readValue(mensaje, NotificacionesDTO.class);
            medicalAlert.setAlertId(id);
            medicalAlert.setDispositivoId(dto.getDispositivoID());
            medicalAlert.setValor(dto.getValor());
            String tipo = dto.getTipo().toUpperCase();
            switch (tipo)
            {
                case "FRECUENCIA CARDIACA":
                    if(dto.getValor()>140){
                        medicalAlert.setLimite(140);
                        medicalAlert.setTipo("FRECUENCIA CARDIACA CRITICA");
                        enviarMensajePrioritario(medicalAlert,0);
                    } else if (dto.getValor()<40) {
                        medicalAlert.setLimite(40);
                        medicalAlert.setTipo("FRECUENCIA CARDIACA CRITICA");
                        enviarMensajePrioritario(medicalAlert,0);
                    }else{
                        medicalAlert.setTipo("FRECUENCIA CARDIACA");
                        medicalAlert.setLimite(40);
                        enviarMensajePrioritario(medicalAlert,10);
                    }
                    break;
                case "OXIGENO":
                    if(dto.getValor()<90){
                        medicalAlert.setTipo("OXIGENO BAJO");
                        medicalAlert.setLimite(90);
                        enviarMensajePrioritario(medicalAlert,0);
                    }else {
                        medicalAlert.setTipo("OXIGENO");
                        medicalAlert.setLimite(90);
                        enviarMensajePrioritario(medicalAlert,10);
                    }
                    break;
                case "PRESION ARTERIAL":
                    if(dto.getValor()>180){
                        medicalAlert.setLimite(180);
                        medicalAlert.setTipo("SISTOLICA");
                        enviarMensajePrioritario(medicalAlert,0);
                    } else if (dto.getValor()>120) {
                        medicalAlert.setLimite(120);
                        medicalAlert.setTipo("DIASTOLICA");
                        enviarMensajePrioritario(medicalAlert,0);
                    }else{
                        medicalAlert.setTipo("PRECION ARTERIAL");
                        medicalAlert.setLimite(120);
                        enviarMensajePrioritario(medicalAlert,10);
                    }
                    break;
                default:
                    log.info("No existe ese tipo de SIGNO VITAL");
                    break;
            }

            if(!medicalAlert.getTipo().isEmpty()){
                medicalAlertRepository.save(medicalAlert);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void enviarMensajePrioritario(MedicalAlert mdto, int prioridad) {
        try {
            // Crear el DTO
            NotificacionGeneralDTO dto = new NotificacionGeneralDTO();
            dto.setDispositivoID(mdto.getDispositivoId());
            dto.setValor(mdto.getValor());
            dto.setTipo(mdto.getTipo());
            dto.setLimite(mdto.getLimite());
            dto.setEventoId(mdto.getAlertId());

            // Serializar a JSON
            String json = objectMapper.writeValueAsString(dto);

            // Crear propiedades del mensaje
            MessageProperties props = new MessageProperties();
            props.setContentType(MessageProperties.CONTENT_TYPE_JSON);
            props.setPriority(prioridad); // Prioridad real

            // Construir mensaje con cuerpo + propiedades
            Message message = new Message(json.getBytes(StandardCharsets.UTF_8), props);

            // Enviar a la cola correspondiente
            if (prioridad == 10) {
                log.info("Signos Vitales Normales no necesita prioridad");
                rabbitTemplate.send("cola_delay_prioridad_10", message);
            } else {
                log.info("ALERTA!!! Signos vitales comprometidos enviado a cola de prioridad");
                rabbitTemplate.send("notificacionesGlobal.cola", message);
            }

        } catch (Exception e) {
            log.error("Error al enviar mensaje prioritario: {}", e.getMessage(), e);
        }
    }

}

