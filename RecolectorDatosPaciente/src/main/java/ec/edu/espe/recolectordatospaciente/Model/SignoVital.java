package ec.edu.espe.recolectordatospaciente.model;

import ec.edu.espe.recolectordatospaciente.Model.DispositivoMedico;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "signos_vitales")
public class SignoVital {

    @Id
    @GeneratedValue
    @Column(name = "id_signo", columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID idSigno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_dispositivo", nullable = false)
    private DispositivoMedico dispositivoMedico;

    @Column(name = "tipo_signo", length = 30, nullable = false)
    private String tipoSigno;

    @Column(nullable = false, precision = 7, scale = 2)
    private BigDecimal valor;

    @Column(name = "fecha_medicion", nullable = false)
    private OffsetDateTime fechaMedicion;

    @Column(name = "fecha_registro")
    private OffsetDateTime fechaRegistro = OffsetDateTime.now();

}
