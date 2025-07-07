package ec.edu.espe.recolectordatospaciente.Config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Slf4j
@Configuration
public class RabbitConfig {

    public static final String COLA_SIGNOS_VITALES = "signosvitales.cola";

    @Bean
    public Queue signosVitalesEnviar() {
        log.info("enviando cola de signos vitales");
        return QueueBuilder.durable(COLA_SIGNOS_VITALES).build();
    }
}