package tugas1.sipas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tugas1.sipas.model.DiagnosisPenyakitModel;

import java.util.List;
import java.util.Optional;


public interface DiagnosisPenyakitDB  extends JpaRepository<DiagnosisPenyakitModel, Long> {
    List<DiagnosisPenyakitModel> findByidDiagnosis(Long idDiagnosis);
    Optional<DiagnosisPenyakitModel> findDiagnosisByidDiagnosis(Long idDiagnosis);
}

