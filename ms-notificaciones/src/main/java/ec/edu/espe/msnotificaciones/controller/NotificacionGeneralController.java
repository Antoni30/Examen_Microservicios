package ec.edu.espe.msnotificaciones.controller;

import ec.edu.espe.msnotificaciones.entity.Notificacion;
import ec.edu.espe.msnotificaciones.entity.NotificacionGeneral;
import ec.edu.espe.msnotificaciones.service.NotificacionesGeneralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/notificacionesGenerales")
public class NotificacionGeneralController {
    @Autowired
    private NotificacionesGeneralService notificacionesGeneralService;
    @GetMapping
    public List<NotificacionGeneral> getNotificaciones(){
        return  notificacionesGeneralService.findAll();
    }
}
