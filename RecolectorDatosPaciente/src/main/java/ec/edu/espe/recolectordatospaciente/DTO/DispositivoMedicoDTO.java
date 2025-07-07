package ec.edu.espe.recolectordatospaciente.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DispositivoMedicoDTO {

    @NotBlank(message = "El ID del dispositivo es obligatorio")
    @Size(max = 50)
    private String idDispositivo;

    @NotBlank(message = "El tipo de dispositivo es obligatorio")
    @Size(max = 30)
    private String tipoDispositivo;

    @Size(max = 100)
    private String ubicacion;

    @Size(max = 20)
    private String estado; // opcional, si no se envía puede ir como "activo"
}
