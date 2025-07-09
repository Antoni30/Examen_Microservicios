package ec.edu.espe.msnotificaciones.listeners;

import com.fasterxml.jackson.databind.ObjectMapper;
import ec.edu.espe.msnotificaciones.DTO.NotificacionGeneralDTO;
import ec.edu.espe.msnotificaciones.DTO.NotificacionesDTO;
import ec.edu.espe.msnotificaciones.service.NotificacionService;
import ec.edu.espe.msnotificaciones.service.NotificacionesGeneralService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NotificacionesGeneralListener {
    @Autowired
    private NotificacionesGeneralService notificacionService;
    @Autowired
    private ObjectMapper mapper;

    @RabbitListener(queues = "notificacionesGlobal.cola")
    public void recibirMensajes(Message mensaje) {
        try {

            Integer prioridad = mensaje.getMessageProperties().getPriority();
            //log.info("Prioridad del mensaje: {}", prioridad != null ? prioridad : "No definida");


            byte[] cuerpo = mensaje.getBody();
            NotificacionGeneralDTO dto = mapper.readValue(cuerpo, NotificacionGeneralDTO.class);
            if(prioridad==0){
                log.info("ALERTA USUARIO {} con {}: mensaje enviado a correo...",dto.getEventoId(),dto.getTipo());
                log.info("Mensaje enviado a correo✅");
            }
            log.info("Analis de signos vitales Guardado");
            notificacionService.guardarNotificacion(dto);

        } catch (Exception e) {
            log.error("Error al procesar el mensaje: {}", e.getMessage(), e);
        }
    }


}
