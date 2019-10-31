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
import java.util.ArrayList;
import java.util.Date;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="diagnosisPasien")
public class DiagnosisPasienModel implements Serializable {
    //ATTRIBUTES
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRelasi;

    @NotNull
    @Column(name="idPasien", nullable = false)
    private Long idPasien;

    @NotNull
    @Column(name="idDiagnosis", nullable = false)
    private Long idDiagnosis;

    @NotNull
    @Column(name="tanggalDiagnosis", nullable = false)
    private String tanggalDiagnosis;

    public Long getIdRelasi() {
        return idRelasi;
    }

    public void setIdRelasi(Long idRelasi) {
        this.idRelasi = idRelasi;
    }

    public Long getIdPasien() {
        return idPasien;
    }

    public void setIdPasien(Long idPasien) {
        this.idPasien = idPasien;
    }

    public Long getIdDiagnosis() {
        return idDiagnosis;
    }

    public void setIdDiagnosis(Long idDiagnosis) {
        this.idDiagnosis = idDiagnosis;
    }

    public String getTanggalDiagnosis() {
        return tanggalDiagnosis;
    }

    public void setTanggalDiagnosis(String tanggalDiagnosis) {
        this.tanggalDiagnosis = tanggalDiagnosis;
    }

    public DiagnosisPasienModel() {
    }

    public DiagnosisPasienModel(@NotNull Long idPasien, @NotNull Long idDiagnosis) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        LocalDateTime now = LocalDateTime.now();
        this.idPasien = idPasien;
        this.idDiagnosis = idDiagnosis;
        this.tanggalDiagnosis = dateFormat.format(now);
    }
}