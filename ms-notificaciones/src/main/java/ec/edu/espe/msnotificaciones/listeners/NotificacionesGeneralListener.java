package ec.edu.espe.msnotificaciones.listeners;

import com.fasterxml.jackson.databind.ObjectMapper;
import ec.edu.espe.msnotificaciones.DTO.NotificacionGeneralDTO;
import ec.edu.espe.msnotificaciones.DTO.NotificacionesDTO;
import ec.edu.espe.msnotificaciones.service.NotificacionService;
import ec.edu.espe.msnotificaciones.service.NotificacionesGeneralService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificacionesGeneralListener {
    @Autowired
    private NotificacionesGeneralService notificacionService;
    @Autowired
    private ObjectMapper mapper;

    @RabbitListener(queues = "notificacionesGlobal.cola")
    public void recibitMensajes(String mensaje) {
        try{
            NotificacionGeneralDTO dto = mapper.readValue(mensaje,NotificacionGeneralDTO.class);
            notificacionService.guardarNotificacion(dto);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
