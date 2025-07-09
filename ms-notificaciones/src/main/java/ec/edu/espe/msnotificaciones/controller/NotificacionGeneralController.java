package ec.edu.espe.msnotificaciones.controller;

import ec.edu.espe.msnotificaciones.DTO.NotificacionGeneralDTO;
import ec.edu.espe.msnotificaciones.entity.Notificacion;
import ec.edu.espe.msnotificaciones.entity.NotificacionGeneral;
import ec.edu.espe.msnotificaciones.service.NotificacionesGeneralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notificacionesGenerales")
public class NotificacionGeneralController {
    @Autowired
    private NotificacionesGeneralService notificacionesGeneralService;
    @GetMapping
    public List<NotificacionGeneral> getNotificaciones(){
        return  notificacionesGeneralService.findAll();
    }
    @GetMapping("/{idAlerta}")
    public ResponseEntity<?> enviarNotificacion(@PathVariable String idAlerta) {
        Optional<NotificacionGeneral> d = notificacionesGeneralService.findById(idAlerta);

        if (d.isPresent()) {
            NotificacionGeneral notificacion = d.get();
            String tipo = notificacion.getTipo();

            return ResponseEntity.ok("Enviado SMS del evento " + idAlerta + " con el tipo: " + tipo);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontró la notificación con ID: " + idAlerta);
        }
    }
}
