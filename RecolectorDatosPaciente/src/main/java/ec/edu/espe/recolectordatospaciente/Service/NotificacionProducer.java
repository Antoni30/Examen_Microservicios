package ec.edu.espe.recolectordatospaciente.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import ec.edu.espe.recolectordatospaciente.DTO.NotificacionesDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Slf4j
@Service
public class NotificacionProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String COLA_NOTIFICACIONES = "NewVitalSignEvent.cola";

    public void enviarSignoVital(NotificacionesDTO notificacion) {
        try {
            String json = objectMapper.writeValueAsString(notificacion);
            log.info("enviando notificacion: {}", json);
            rabbitTemplate.convertAndSend(COLA_NOTIFICACIONES, json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
