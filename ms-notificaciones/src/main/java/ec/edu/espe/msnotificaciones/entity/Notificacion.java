package ec.edu.espe.msnotificaciones.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Notificacion {
    @Id
    private String eventoId;
    private String dispositivoID;
    private String tipo;
    private Integer valor;
    private LocalDateTime fecha;

    @PrePersist
    public void prePersist() {
        this.fecha = LocalDateTime.now();
    }
}
