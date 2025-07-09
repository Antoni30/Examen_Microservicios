package ec.edu.espe.msanalizadorsalud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MsAnalizadorSaludApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsAnalizadorSaludApplication.class, args);
    }

}
