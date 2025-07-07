package ec.edu.espe.recolectordatospaciente.Service;


import ec.edu.espe.recolectordatospaciente.DTO.DispositivoMedicoDTO;
import ec.edu.espe.recolectordatospaciente.Model.DispositivoMedico;
import ec.edu.espe.recolectordatospaciente.Repository.DispositivoMedicoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class DispositivoMedicoService {

    @Autowired
    private DispositivoMedicoRepository dispositivoMedicoRepository;

    public DispositivoMedico crearDispositivo(DispositivoMedicoDTO dto) {
        if (dispositivoMedicoRepository.existsById(dto.getIdDispositivo())) {
            throw new RuntimeException("El dispositivo ya existe con id: " + dto.getIdDispositivo());
        }

        DispositivoMedico dispositivo = new DispositivoMedico();
        dispositivo.setIdDispositivo(dto.getIdDispositivo());
        dispositivo.setTipoDispositivo(dto.getTipoDispositivo());
        dispositivo.setUbicacion(dto.getUbicacion());
        dispositivo.setEstado(dto.getEstado() != null ? dto.getEstado() : "activo");

        return dispositivoMedicoRepository.save(dispositivo);
    }

    public DispositivoMedico actualizarDispositivo(String id, DispositivoMedicoDTO dto) {
        DispositivoMedico dispositivo = dispositivoMedicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe dispositivo con id: " + id));

        dispositivo.setTipoDispositivo(dto.getTipoDispositivo());
        dispositivo.setUbicacion(dto.getUbicacion());
        dispositivo.setEstado(dto.getEstado() != null ? dto.getEstado() : dispositivo.getEstado());

        return dispositivoMedicoRepository.save(dispositivo);
    }

    public void eliminarDispositivo(String id) {
        DispositivoMedico dispositivo = dispositivoMedicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe dispositivo con id: " + id));

        dispositivoMedicoRepository.delete(dispositivo);
    }

    public List<DispositivoMedico> listarDispositivos() {
        return dispositivoMedicoRepository.findAll();
    }
}
