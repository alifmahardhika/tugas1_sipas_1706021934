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
import java.util.List;

@Entity
@Table(name="pasienAsuransi")
public class PasienAsuransiModel implements Serializable {
    //ATTRIBUTES
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRelasi;

    @NotNull
    @Column(name="idPasien", nullable = false)
    private BigInteger idPasien;

    @NotNull
    @Column(name="idAsuransi", nullable = false)
    private BigInteger idAsuransi;

    //SETTER &GETTER

    public Integer getIdRelasi() {
        return idRelasi;
    }

    public void setIdRelasi(Integer idRelasi) {
        this.idRelasi = idRelasi;
    }

    public BigInteger getIdPasien() {
        return idPasien;
    }

    public void setIdPasien(BigInteger idPasien) {
        this.idPasien = idPasien;
    }

    public BigInteger getIdAsuransi() {
        return idAsuransi;
    }

    public void setIdAsuransi(BigInteger idAsuransi) {
        this.idAsuransi = idAsuransi;
    }

    public PasienAsuransiModel(@NotNull BigInteger idPasien, @NotNull BigInteger idAsuransi) {
        this.idPasien = idPasien;
        this.idAsuransi = idAsuransi;
    }
}
