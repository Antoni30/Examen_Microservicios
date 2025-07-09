package ec.edu.espe.msnotificaciones.service;

import ec.edu.espe.msnotificaciones.DTO.NotificacionGeneralDTO;
import ec.edu.espe.msnotificaciones.entity.NotificacionGeneral;
import ec.edu.espe.msnotificaciones.repository.NotificacionGeneralRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificacionesGeneralService {
     @Autowired
    private NotificacionGeneralRepository notificacionGeneralRepository;

     public List<NotificacionGeneral> findAll(){
         return notificacionGeneralRepository.findAll();
     }
     public Optional<NotificacionGeneral> findById(String id){
         return notificacionGeneralRepository.findById(id);
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
