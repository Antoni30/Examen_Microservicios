package ec.edu.espe.msnotificaciones.service;

import ec.edu.espe.msnotificaciones.DTO.NotificacionGeneralDTO;
import ec.edu.espe.msnotificaciones.DTO.NotificacionesDTO;
import ec.edu.espe.msnotificaciones.entity.Notificacion;
import ec.edu.espe.msnotificaciones.entity.NotificacionGeneral;
import ec.edu.espe.msnotificaciones.repository.NotificacionGeneralRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificacionesGeneralService {
     @Autowired
    private NotificacionGeneralRepository notificacionGeneralRepository;

     public List<NotificacionGeneral> findAll(){
         return notificacionGeneralRepository.findAll();
     }

    public NotificacionGeneral guardarNotificacion(NotificacionGeneralDTO notificacionesDTO){
        NotificacionGeneral n = new NotificacionGeneral();
        n.setValor(notificacionesDTO.getValor());
        n.setTipo(notificacionesDTO.getTipo());
        n.setDispositivoID(notificacionesDTO.getDispositivoID());
        n.setEventoId(notificacionesDTO.getEventoId());
        n.setLimite(notificacionesDTO.getLimite());
        notificacionGeneralRepository.save(n);
        return n;
    }
}
