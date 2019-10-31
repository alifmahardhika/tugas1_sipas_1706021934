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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="diagnosisPenyakit")
public class DiagnosisPenyakitModel implements Serializable {
    //ATTRIBUTES
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long idDiagnosis;

    @NotNull
    @Size(max =255)
    @Column(name="namaDiagnosis", nullable = false)
    private String namaDiagnosis;

    @NotNull
    @Size(max =255)
    @Column(name="kode", nullable = false, unique = true)
    private String kode;
//
//    @ManyToMany
//    @JoinTable(
//            name="daftarAsuransi",
//            joinColumns= @JoinColumn(name = "idPasien"),
//            inverseJoinColumns = @JoinColumn(name="idAsuransi"))
//    List<AsuransiModel> listAsuransi;


    public Long getIdPenyakit() {
        return idDiagnosis;
    }

    public void setIdPenyakit(Long idDiagnosis) {
        this.idDiagnosis = idDiagnosis;
    }

    public String getNamaDiagnosis() {
        return namaDiagnosis;
    }

    public void setNamaDiagnosis(String namaDiagnosis) {
        this.namaDiagnosis = namaDiagnosis;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

//    public DiagnosisPenyakitModel() {
//    }
//
//    public DiagnosisPenyakitModel(@NotNull @Size(max = 255) String namaDiagnosis, @NotNull @Size(max = 255) String kode) {
//
//        this.namaDiagnosis = namaDiagnosis;
//        this.kode = kode;
//    }
}