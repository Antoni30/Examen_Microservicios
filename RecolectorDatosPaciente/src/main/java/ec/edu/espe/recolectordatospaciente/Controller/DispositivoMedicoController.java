package ec.edu.espe.recolectordatospaciente.Controller;

import ec.edu.espe.recolectordatospaciente.DTO.DispositivoMedicoDTO;
import ec.edu.espe.recolectordatospaciente.Model.DispositivoMedico;
import ec.edu.espe.recolectordatospaciente.Service.DispositivoMedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dispositivos")
public class DispositivoMedicoController {

    @Autowired
    private DispositivoMedicoService dispositivoMedicoService;

    @PostMapping
    public ResponseEntity<DispositivoMedico> crearDispositivo(@RequestBody DispositivoMedicoDTO dto) {
        DispositivoMedico creado = dispositivoMedicoService.crearDispositivo(dto);
        return ResponseEntity.ok(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DispositivoMedico> actualizarDispositivo(@PathVariable String id, @RequestBody DispositivoMedicoDTO dto) {
        DispositivoMedico actualizado = dispositivoMedicoService.actualizarDispositivo(id, dto);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDispositivo(@PathVariable String id) {
        dispositivoMedicoService.eliminarDispositivo(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<DispositivoMedico>> listarDispositivos() {
        List<DispositivoMedico> lista = dispositivoMedicoService.listarDispositivos();
        return ResponseEntity.ok(lista);
    }
}
