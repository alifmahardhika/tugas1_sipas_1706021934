package tugas1.sipas.repository;

import tugas1.sipas.model.PasienModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Optional;

@Repository
public interface PasienDB extends JpaRepository<PasienModel, Long>{
    Optional<PasienModel> findByIdPasien(Long idPasien);
}

