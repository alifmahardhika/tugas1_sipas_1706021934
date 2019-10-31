package tugas1.sipas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tugas1.sipas.model.DiagnosisPenyakitModel;

import tugas1.sipas.repository.DiagnosisPenyakitDB;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DiagnosisPenyakitServiceImpl implements DiagnosisPenyakitService {
    @Autowired
    private DiagnosisPenyakitDB diagnosisDB;

    @Override
    public List<DiagnosisPenyakitModel> getListDiagnosis() {
        return diagnosisDB.findAll();
    }

    @Override
    public Optional<DiagnosisPenyakitModel> getDiagnosisById(Long idDiagnosis) {
        return diagnosisDB.findDiagnosisByidDiagnosis(idDiagnosis);
    }

    @Override
    public void tambahDiagnosisPenyakit(DiagnosisPenyakitModel diagnosis) {
        diagnosisDB.save(diagnosis);
    }

    @Override
    public void hapusDiagnosisPenyakit(Long idDiagnosis) {
        diagnosisDB.deleteById(idDiagnosis);
    }
}
