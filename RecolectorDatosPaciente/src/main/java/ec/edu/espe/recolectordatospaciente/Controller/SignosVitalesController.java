package ec.edu.espe.recolectordatospaciente.Controller;

import ec.edu.espe.recolectordatospaciente.DTO.SignoVitalDTO;
import ec.edu.espe.recolectordatospaciente.model.SignoVital;
import ec.edu.espe.recolectordatospaciente.Service.SignoVitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/vital-signs")
public class SignosVitalesController {

    @Autowired
    private SignoVitalService signoVitalService;

    @PostMapping
    public ResponseEntity<SignoVital> crearSignoVital(@RequestBody SignoVitalDTO dto) {
        SignoVital creado = signoVitalService.crearSignoVital(dto);
        return ResponseEntity.ok(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SignoVital> actualizarSignoVital(@PathVariable UUID id, @RequestBody SignoVitalDTO dto) {
        SignoVital actualizado = signoVitalService.actualizarSignoVital(id, dto);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarSignoVital(@PathVariable UUID id) {
        signoVitalService.eliminarSignoVital(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<SignoVital>> listarTodos() {
        List<SignoVital> lista = signoVitalService.listarTodos();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/dispositivo/{deviceId}")
    public ResponseEntity<List<SignoVital>> listarPorDispositivo(@PathVariable String deviceId) {
        List<SignoVital> lista = signoVitalService.listarSignosPorDispositivo(deviceId);
        return ResponseEntity.ok(lista);
    }
}
