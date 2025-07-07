package ec.edu.espe.msnotificaciones.service;


import ec.edu.espe.msnotificaciones.DTO.NotificacionesDTO;
import ec.edu.espe.msnotificaciones.entity.Notificacion;
import ec.edu.espe.msnotificaciones.repository.NotificacionesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class NotificacionService {

    @Autowired
    private NotificacionesRepository notificacionesRepository;

    public List<Notificacion> listarNotificaciones(){
        log.info("Listando notificaciones");
        return notificacionesRepository.findAll();
    }

    public Notificacion guardarNotificacion(NotificacionesDTO notificacionesDTO){
        Notificacion n = new Notificacion();
        n.setValor(notificacionesDTO.getValor());
        n.setTipo(notificacionesDTO.getTipo());
        n.setDispositivoID(notificacionesDTO.getDispositivoID());
        n.setEventoId(notificacionesDTO.getEventoId());
        notificacionesRepository.save(n);
        return n;
    }

}
