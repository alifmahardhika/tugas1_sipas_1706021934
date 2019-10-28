package tugas1.sipas.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

@Entity
@Table(name = "asuransi")
public class AsuransiModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAsuransi;

    @NotNull
    @Size(max = 20)
    @Column(name = "nama", nullable = false)
    private String nama;

    @NotNull
    @Size(max = 20)
    @Column(name = "jenis", nullable = false)
    private String jenis;

    @ManyToMany(mappedBy="listAsuransi")
    List<PasienModel> listPasien;

    public Long getIdAsuransi() {
        return idAsuransi;
    }

    public void setIdAsuransi(Long idAsuransi) {
        this.idAsuransi = idAsuransi;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public List<PasienModel> getListPasien() {
        return listPasien;
    }

    public void setListPasien(List<PasienModel> listPasien) {
        this.listPasien = listPasien;
    }
}
