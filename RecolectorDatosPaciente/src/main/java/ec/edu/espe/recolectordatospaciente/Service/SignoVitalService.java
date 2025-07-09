package ec.edu.espe.recolectordatospaciente.Service;

import ec.edu.espe.recolectordatospaciente.DTO.NotificacionesDTO;
import ec.edu.espe.recolectordatospaciente.DTO.SignoVitalDTO;
import ec.edu.espe.recolectordatospaciente.Model.DispositivoMedico;
import ec.edu.espe.recolectordatospaciente.Repository.DispositivoMedicoRepository;
import ec.edu.espe.recolectordatospaciente.Repository.SignoVitalRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import         ec.edu.espe.recolectordatospaciente.model.SignoVital;
import org.springframework.web.bind.annotation.PathVariable;

@Service
@Transactional
public class SignoVitalService {

    @Autowired
    private SignoVitalRepository signoVitalRepository;

    @Autowired
    private DispositivoMedicoRepository dispositivoMedicoRepository;

    @Autowired
    private NotificacionProducer notificacionProducer;


    public ec.edu.espe.recolectordatospaciente.model.SignoVital crearSignoVital(SignoVitalDTO dto) {
        DispositivoMedico dispositivo = dispositivoMedicoRepository.findById(dto.getIdDispositivo())
                .orElseThrow(() -> new RuntimeException("No existe dispositivo con id: " + dto.getIdDispositivo()));

        SignoVital signo = new SignoVital();
        signo.setDispositivoMedico(dispositivo);
        signo.setTipoSigno(dto.getTipoSigno());
        signo.setValor(dto.getValor());
        signo.setFechaMedicion(dto.getFechaMedicion() != null ? dto.getFechaMedicion() : OffsetDateTime.now());
        signo.setFechaRegistro(OffsetDateTime.now());

        SignoVital guardado = signoVitalRepository.save(signo);

        enviarNotificacion(guardado);


        return guardado;
    }

    public SignoVital findSignoVitalById(@PathVariable UUID id) {
        return signoVitalRepository.findById(id).orElse(null);
    }

    public SignoVital actualizarSignoVital(UUID idSigno, SignoVitalDTO dto) {
        SignoVital signo = signoVitalRepository.findById(idSigno)
                .orElseThrow(() -> new RuntimeException("No existe signo vital con id: " + idSigno));

        DispositivoMedico dispositivo = dispositivoMedicoRepository.findById(dto.getIdDispositivo())
                .orElseThrow(() -> new RuntimeException("No existe dispositivo con id: " + dto.getIdDispositivo()));

        signo.setDispositivoMedico(dispositivo);
        signo.setTipoSigno(dto.getTipoSigno());
        signo.setValor(dto.getValor());
        signo.setFechaMedicion(dto.getFechaMedicion() != null ? dto.getFechaMedicion() : signo.getFechaMedicion());

        SignoVital actualizado = signoVitalRepository.save(signo);

        enviarNotificacion(actualizado);

        return actualizado;
    }

    public void eliminarSignoVital(UUID idSigno) {
        SignoVital signo = signoVitalRepository.findById(idSigno)
                .orElseThrow(() -> new RuntimeException("No existe signo vital con id: " + idSigno));

        signoVitalRepository.delete(signo);
    }

    public List<SignoVital> listarTodos() {
        return signoVitalRepository.findAll();
    }

    public List<SignoVital> listarSignosPorDispositivo(String idDispositivo) {
        return signoVitalRepository.findByDispositivoMedico_IdDispositivoOrderByFechaMedicionDesc(idDispositivo);
    }

    private void enviarNotificacion(SignoVital signo) {
        NotificacionesDTO notificacion = new NotificacionesDTO(
                UUID.randomUUID().toString(),
                signo.getDispositivoMedico().getIdDispositivo(),
                signo.getTipoSigno(),
                signo.getValor().intValue()
        );

        notificacionProducer.enviarSignoVital(notificacion);
    }
}