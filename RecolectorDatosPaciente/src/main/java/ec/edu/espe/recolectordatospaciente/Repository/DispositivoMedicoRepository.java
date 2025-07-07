package ec.edu.espe.recolectordatospaciente.Repository;


import ec.edu.espe.recolectordatospaciente.Model.DispositivoMedico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DispositivoMedicoRepository extends JpaRepository<DispositivoMedico, String> {
    // El ID es String (idDispositivo)
}