package ec.edu.espe.msanalizadorsalud.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificacionGeneralDTO {
    private String eventoId;
    private String dispositivoID;
    private String tipo;
    private Integer valor;
    private Integer limite;
}
