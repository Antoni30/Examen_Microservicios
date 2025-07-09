package ec.edu.espe.msnotificaciones.listeners;

import com.fasterxml.jackson.databind.ObjectMapper;

import ec.edu.espe.msnotificaciones.DTO.NotificacionesDTO;
import ec.edu.espe.msnotificaciones.service.NotificacionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NotificacionListener {
    @Autowired
    private NotificacionService notificacionService;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = "NewVitalSignEvent.cola")
    public void recibitMensajes(String mensaje) {
        try{
            log.info("Nuevo Signo Vital Tomado");
            NotificacionesDTO dto = mapper.readValue(mensaje,NotificacionesDTO.class);
            String json = mapper.writeValueAsString(dto);
            rabbitTemplate.convertAndSend("cola_microservicio", json);
            notificacionService.guardarNotificacion(dto);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
