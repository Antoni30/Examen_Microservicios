package ec.edu.espe.msanalizadorsalud.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "medical_alerts")
public class MedicalAlert {

    @Id
    private String alertId;
    private String dispositivoId;
    private String tipo;
    private Integer valor;
    private Integer limite;
    private LocalDateTime fecha;

    @PrePersist
    public void prePersist() {
        this.fecha = LocalDateTime.now();
    }
}
