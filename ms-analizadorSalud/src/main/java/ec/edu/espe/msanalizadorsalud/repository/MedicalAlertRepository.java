package ec.edu.espe.msanalizadorsalud.repository;

import ec.edu.espe.msanalizadorsalud.entity.MedicalAlert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MedicalAlertRepository extends JpaRepository<MedicalAlert,String> {
    List<MedicalAlert> findByFechaAfter(LocalDateTime fecha);

}
