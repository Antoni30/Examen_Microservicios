package ec.edu.espe.recolectordatospaciente.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "dispositivos_medicos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DispositivoMedico {

    @Id
    @Column(name = "id_dispositivo", length = 50)
    private String idDispositivo;

    @Column(name = "tipo_dispositivo", length = 30, nullable = false)
    private String tipoDispositivo;

    @Column(length = 100)
    private String ubicacion;

    @Column(length = 20)
    private String estado = "activo";

}
