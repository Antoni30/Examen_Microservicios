package ec.edu.espe.msnotificaciones.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue ColaNotificacion () {
        return QueueBuilder.durable("NewVitalSignEvent.cola").build();
    }

    @Bean
    public Queue ColaNotificacionGlobal () {
        return QueueBuilder.durable("notificacionesGlobal.cola")
                .withArgument("x-max-priority", 10)
                .build();
    }

    @Bean
    public Queue colaMSAnalisis() {
        return new Queue("cola_microservicio");
    }

    @Bean
    public Queue delayQueue() {
        return QueueBuilder.durable("cola_delay_prioridad_10")
                .withArgument("x-dead-letter-exchange", "")  // default exchange
                .withArgument("x-dead-letter-routing-key", "notificacionesGlobal.cola")
                .withArgument("x-message-ttl", 60000) // 1 minutos en milisegundos
                .build();
    }


}
