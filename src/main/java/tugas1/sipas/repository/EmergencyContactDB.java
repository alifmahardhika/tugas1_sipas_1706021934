package tugas1.sipas.repository;

import tugas1.sipas.model.EmergencyContactModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Optional;

@Repository
public interface EmergencyContactDB extends JpaRepository<EmergencyContactModel, Long>{
    Optional<EmergencyContactModel> findById(Long idContact);
}
