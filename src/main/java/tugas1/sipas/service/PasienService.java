package tugas1.sipas.service;

import java.util.List;
import java.util.Optional;

import tugas1.sipas.model.*;


public interface PasienService {
    //Method untuk menambah pasien
    void addPasien(PasienModel pasien);


    // Method untuk mendapatkan semua data Pasien yang tersimpan
    List<PasienModel> getPasienList();

    // Method untuk mendapatkan data sebuah Pasien berdasarkan idPasien
    Optional<PasienModel> getPasienByIdPasien(Long idPasien);

    PasienModel changePasien(PasienModel pasienModel);

    void removePasien(Long idPasien);

    void addEmergencyContact(EmergencyContactModel contact);


    void addAsuransiRelation(PasienAsuransiModel relasi);

    List<PasienAsuransiModel> findAsuransiPasien();

    void removeRelasi(Long idRelasi);


    void addDiagnosisPasien(DiagnosisPasienModel diagnosis);

    List<DiagnosisPasienModel> findDiagnosisPasien();
    void removeRelasiDiag(Long idRelasi);
}