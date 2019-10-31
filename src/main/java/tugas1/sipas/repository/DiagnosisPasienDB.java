package tugas1.sipas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tugas1.sipas.model.DiagnosisPasienModel;
import tugas1.sipas.model.PasienAsuransiModel;

import java.util.Optional;
@Repository
public interface DiagnosisPasienDB extends JpaRepository<DiagnosisPasienModel, Long>{
    Optional<DiagnosisPasienModel> findByIdRelasi(Long idRelasi);
}

