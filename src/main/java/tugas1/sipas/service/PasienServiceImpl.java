package tugas1.sipas.service;

import tugas1.sipas.model.*;
import tugas1.sipas.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PasienServiceImpl implements PasienService {
    @Autowired
    private PasienDB pasienDB;

    @Autowired
    private EmergencyContactDB emergencyContactDB;

    @Autowired
    private PasienAsuransiDB pasienAsuransiDB;

    @Autowired
    private DiagnosisPasienDB diagnosisPasienDB;

    @Override
    public void addPasien(PasienModel pasien) {pasienDB.save(pasien);}

    @Override
    public void addEmergencyContact(EmergencyContactModel contact) {emergencyContactDB.save(contact);}

    @Override
    public void addDiagnosisPasien(DiagnosisPasienModel diagnosis) {diagnosisPasienDB.save(diagnosis);}


    @Override
    public void addAsuransiRelation(PasienAsuransiModel relasi) {pasienAsuransiDB.save(relasi);}

    @Override
    public void removePasien(Long idPasien) {
        pasienDB.deleteById(idPasien);
    }

    @Override
    public List<PasienModel> getPasienList() {
        List<PasienModel> list = pasienDB.findAll();
        return list;
    }
//
//    @Override
//    public void deletePasien(PasienModel pasien) {
//
//
//    }
//
//    @Override
//    public Boolean findPasien(String idPasien) {
//        return null;
//    }

    @Override
    public Optional<PasienModel> getPasienByIdPasien(Long idPasien){
        return pasienDB.findByIdPasien(idPasien);
    }

    @Override
    public PasienModel changePasien(PasienModel pasienModel) {
        // mengambil object pasien yang ingin diubah
        PasienModel targetPasien = pasienDB.findById(pasienModel.getIdPasien()).get();

        try{
            targetPasien.setNama(pasienModel.getNama());
            targetPasien.setNik((pasienModel.getNik()));
            targetPasien.setJenisKelamin(pasienModel.getJenisKelamin());
            targetPasien.setTanggalLahir((pasienModel.getTanggalLahir()));
            targetPasien.setTempatLahir((pasienModel.getTempatLahir()));
            pasienDB.save(targetPasien);
            return targetPasien;
        } catch (NullPointerException nullException){
            return null;
        }
    }

    @Override
    public List<PasienAsuransiModel> findAsuransiPasien(){
        List<PasienAsuransiModel> list = pasienAsuransiDB.findAll();
        return list;
    }

    @Override
    public List<DiagnosisPasienModel> findDiagnosisPasien(){
        List<DiagnosisPasienModel> list = diagnosisPasienDB.findAll();
        return list;
    }

    @Override
    public void removeRelasi(Long idRelasi) {
        pasienAsuransiDB.deleteById(idRelasi);
    }

    @Override
    public void removeRelasiDiag(Long idRelasi) {
        diagnosisPasienDB.deleteById(idRelasi);
    }



}

