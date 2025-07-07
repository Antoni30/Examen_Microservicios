package ec.edu.espe.recolectordatospaciente.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SignoVitalRepository extends JpaRepository<ec.edu.espe.recolectordatospaciente.model.SignoVital, UUID> {
    // Método para obtener historial por dispositivo
    List<ec.edu.espe.recolectordatospaciente.model.SignoVital> findByDispositivoMedico_IdDispositivoOrderByFechaMedicionDesc(String idDispositivo);
}