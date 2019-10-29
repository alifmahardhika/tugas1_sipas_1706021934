package tugas1.sipas.repository;

import tugas1.sipas.model.PasienAsuransiModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PasienAsuransiDB extends JpaRepository<PasienAsuransiModel, Long>{
    Optional<PasienAsuransiModel> findByIdPasien(Long idRelasi);
}

