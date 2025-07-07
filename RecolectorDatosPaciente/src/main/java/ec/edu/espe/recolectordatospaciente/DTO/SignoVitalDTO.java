package ec.edu.espe.recolectordatospaciente.DTO;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
public class SignoVitalDTO {

    @NotBlank(message = "El ID del dispositivo es obligatorio")
    private String idDispositivo;  // para relacionar con DispositivoMedico

    @NotBlank(message = "El tipo de signo es obligatorio")
    @Size(max = 30)
    private String tipoSigno;

    @NotNull(message = "El valor es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El valor debe ser positivo")
    private BigDecimal valor;

    @NotNull(message = "La fecha de medición es obligatoria")
    private OffsetDateTime fechaMedicion;
}
