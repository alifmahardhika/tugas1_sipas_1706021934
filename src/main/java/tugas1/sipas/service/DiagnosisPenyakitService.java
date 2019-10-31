package tugas1.sipas.service;

import tugas1.sipas.model.DiagnosisPenyakitModel;

import java.util.List;
import java.util.Optional;

public interface DiagnosisPenyakitService {
    List<DiagnosisPenyakitModel> getListDiagnosis();
    Optional<DiagnosisPenyakitModel> getDiagnosisById(Long idDiagnosis);
    void tambahDiagnosisPenyakit(DiagnosisPenyakitModel diagnosis);
    void hapusDiagnosisPenyakit(Long idDiagnosis);
}


