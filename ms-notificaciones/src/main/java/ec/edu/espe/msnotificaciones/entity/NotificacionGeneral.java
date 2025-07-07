package ec.edu.espe.msnotificaciones.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.Data;

import java.time.LocalDateTime;
@Entity
@Data
public class NotificacionGeneral {
    @Id
    private String eventoId;
    private String dispositivoID;
    private String tipo;
    private Integer valor;
    private Integer limite;
    private LocalDateTime fecha;

    @PrePersist
    public void prePersist() {
        this.fecha = LocalDateTime.now();
    }
}
