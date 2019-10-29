package tugas1.sipas.service;

import tugas1.sipas.model.EmergencyContactModel;
import tugas1.sipas.model.PasienAsuransiModel;
import tugas1.sipas.model.PasienModel;
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

    @Override
    public void addPasien(PasienModel pasien) {pasienDB.save(pasien);}

    @Override
    public void addEmergencyContact(EmergencyContactModel contact) {emergencyContactDB.save(contact);}

    @Override
    public void addAsuransiRelation(PasienAsuransiModel relasi) {pasienAsuransiDB.save(relasi);}

    @Override
    public void removePasien(Long idPasien) {
        pasienDB.deleteById(idPasien);
    }

    @Override
    public List<PasienModel> getPasienList() {
        List<PasienModel> list = pasienDB.findAll();
        list.sort((o1, o2) -> o1.getNama().compareTo(o2.getNama()));
        return list;

    }

    @Override
    public void deletePasien(PasienModel pasien) {


    }

    @Override
    public Boolean findPasien(String idPasien) {
        return null;
    }

    @Override
    public Optional<PasienModel> getPasienByIdPasien(Long idPasien){
        return pasienDB.findByIdPasien(idPasien);
    }

    @Override
    public PasienModel changePasien(PasienModel pasienModel) {
        // mengambil object pasien yang ingin diubah
//        PasienModel targetPasien = pasienDB.findById(pasienModel.getIdPasien()).get();
//
//        try{
//            targetPasien.setNama(pasienModel.getNama());
//            targetPasien.setAlamat((pasienModel.getAlamat()));
//            targetPasien.setNomorTelepon(pasienModel.getNomorTelepon());
//            pasienDB.save(targetPasien);
//            return targetPasien;
//        } catch (NullPointerException nullException){
//            return null;
//        }
        return null;

    }


}

