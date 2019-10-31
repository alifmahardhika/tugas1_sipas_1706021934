package tugas1.sipas.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.format.annotation.DateTimeFormat;
import tugas1.sipas.model.EmergencyContactModel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="pasien")
public class PasienModel implements Serializable {
    //ATTRIBUTES
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPasien;

    @NotNull
    @Size(max =255)
    @Column(name="nama", nullable = false)
    private String nama;

    @NotNull
    @Column(name="jenisKelamin", nullable = false)
    private Integer jenisKelamin;

    @NotNull
    @Size(max =255)
    @Column(name="nik", nullable = false)
    private String nik;

//    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @NotNull
    @Column(name="tanggalLahir", nullable = false)
    private String tanggalLahir;

    @NotNull
    @Size(max =255)
    @Column(name="tempatLahir", nullable = false)
    private String tempatLahir;

    @NotNull
    @Column(name="uniqueCode", nullable = false)
    private String uniqueCode;


    @ManyToMany
    @JoinTable(
            name="daftarAsuransi",
            joinColumns= @JoinColumn(name = "idPasien"),
            inverseJoinColumns = @JoinColumn(name="idAsuransi"))
    List<AsuransiModel> listAsuransi;

    @NotNull
    private String idEmergencyContact;

    @NotNull
    @Column(name="idAsuransi")
    private String idAsuransi;




    //SETTER & GETTER
    public Long getIdPasien() {
        return idPasien;
    }
    public void setIdPasien(Long idPasien) {
        this.idPasien = idPasien;
    }
    public String getNama() {
        return nama;
    }
    public void setNama(String nama) {
        this.nama = nama;
    }
    public Integer getJenisKelamin() {
        return jenisKelamin;
    }
    public void setJenisKelamin(Integer jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }
    public String getNik() {
        return nik;
    }
    public void setNik(String nik) {
        this.nik = nik;
    }
    public String getTanggalLahir() {
        return tanggalLahir;
    }
    public void setTanggalLahir(String tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }
    public String getTempatLahir() {
        return tempatLahir;
    }
    public void setTempatLahir(String tempatLahir) {
        this.tempatLahir = tempatLahir;
    }
    public String getUniqueCode() {
        return uniqueCode;
    }
    public void setUniqueCode(String uniqueCode) {
        this.uniqueCode = uniqueCode;
    }
    public List<AsuransiModel> getListAsuransi() {
        return listAsuransi;
    }
    public void setListAsuransi(List<AsuransiModel> listAsuransi) {
        this.listAsuransi = listAsuransi;
    }

    public String getIdEmergencyContact() {
        return idEmergencyContact;
    }

    public void setIdEmergencyContact(String idEmergencyContact) {
        this.idEmergencyContact = idEmergencyContact;
    }

    public String getIdAsuransi() {
        return idAsuransi;
    }

    public void setIdAsuransi(String idAsuransi) {
        this.idAsuransi = idAsuransi;
    }

    //METHOD LAINNYA
    public String getNoHpContact(List<EmergencyContactModel> listContact){
        String result = "";
        for (EmergencyContactModel contact : listContact){
            if (idEmergencyContact.equalsIgnoreCase(Long.toString(contact.getIdContact()))){
                result += contact.getNoHp();
            }
        }
        return result;
    }

    public EmergencyContactModel getEmergencyContact(List<EmergencyContactModel> listContact){
        EmergencyContactModel contact = null;
        for (EmergencyContactModel contact2 : listContact){
            if (idEmergencyContact.equalsIgnoreCase(Long.toString(contact.getIdContact()))){
                contact = contact2;
            }
        }
        return contact;
    }
}
