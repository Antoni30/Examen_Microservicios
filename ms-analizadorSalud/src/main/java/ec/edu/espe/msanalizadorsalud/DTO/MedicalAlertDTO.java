package ec.edu.espe.msanalizadorsalud.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicalAlertDTO {
    private String alertId;
    private String dispositivoId;
    private String tipo;
    private Integer valor;
    private Integer limite;
}
