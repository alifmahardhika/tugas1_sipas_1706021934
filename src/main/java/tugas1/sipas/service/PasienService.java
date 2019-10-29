package tugas1.sipas.service;

import java.util.List;
import java.util.Optional;

import tugas1.sipas.model.EmergencyContactModel;
import tugas1.sipas.model.PasienAsuransiModel;
import tugas1.sipas.model.PasienModel;


public interface PasienService {
    //Method untuk menambah pasien
    void addPasien(PasienModel pasien);


    // Method untuk mendapatkan semua data Pasien yang tersimpan
    List<PasienModel> getPasienList();

    // Method untuk mendapatkan data sebuah Pasien berdasarkan idPasien
    Optional<PasienModel> getPasienByIdPasien(Long idPasien);

    PasienModel changePasien(PasienModel pasienModel);

    void deletePasien(PasienModel pasienModel);

    void removePasien(Long idPasien);

    Boolean findPasien(String idPasien);

    void addEmergencyContact(EmergencyContactModel contact);

    void addAsuransiRelation(PasienAsuransiModel relasi);
}