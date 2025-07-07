package ec.edu.espe.msnotificaciones.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificacionesDTO {
    private String eventoId;
    private String dispositivoID;
    private String tipo;
    private Integer valor;
}
