package tugas1.sipas.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.format.annotation.DateTimeFormat;

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
    private Integer idPasien;

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

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @NotNull
    @Column(name="tanggalLahir", nullable = false)
    private Date tanggalLahir;

    @NotNull
    @Size(max =255)
    @Column(name="tempatLahir", nullable = false)
    private String tempatLahir;

    @NotNull
    @UniqueElements
    @Size(max =255)
    @Column(name="uniqueCode", nullable = false)
    private String uniqueCode;

    @ManyToMany
    @JoinTable(
            name="daftarAsuransi",
            joinColumns= @JoinColumn(name = "idPasien"),
            inverseJoinColumns = @JoinColumn(name="idAsuransi"))
    List<AsuransiModel> listAsuransi;

    public EmergencyContactModel getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(EmergencyContactModel emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "EmergencyContactId", referencedColumnName = "idContact")
    private EmergencyContactModel emergencyContact;

//    private AsuransiModel asuransi;
    ////////////////////////////////kyknya harusnya relationship
//    EmergencyContact emergencyContact = new EmergencyContact();
//    ArrayList<Asuransi> listAsuransi = new ArrayList<>();
//    ArrayList<Penyakit> listPenyakit = new ArrayList<>();

    //CONSTRUCTOR
//    public PasienModel(@NotNull @Size(max = 255) String nama, @NotNull Integer jenisKelamin, @NotNull @Size(max = 255) String nik, @NotNull Date tanggalLahir, @NotNull @Size(max = 255) String tempatLahir) {
//        this.nama = nama;
//        this.jenisKelamin = jenisKelamin;
//        this.nik = nik;
//        this.tanggalLahir = tanggalLahir;
//        this.tempatLahir = tempatLahir;
//    }

    //SETTER & GETTER
    public Integer getIdPasien() {
        return idPasien;
    }
    public void setIdPasien(Integer idPasien) {
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
    public Date getTanggalLahir() {
        return tanggalLahir;
    }
    public void setTanggalLahir(Date tanggalLahir) {
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

    //METHODS
//    public String getSplitTanggalLahir(){
//        String temp = "";
//        String[] arr = getTanggalLahir().split("/");
//        for (String val: arr){temp = temp + val;}
//        return  temp;
//    }
}
