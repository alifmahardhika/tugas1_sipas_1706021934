package tugas1.sipas.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

@Entity
@Table(name = "emergencyContact")
public class EmergencyContactModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idContact;

    @NotNull
    @Size(max = 20)
    @Column(name = "nama", nullable = false)
    private String nama;

    @NotNull
    @Size(max =255)
    @Column(name="nik", nullable = false)
    private String nik;

    @NotNull
    @Size(max = 20)
    @Column(name = "noHp", nullable = false)
    private String noHp;

//    @OneToOne(mappedBy = "emergencyContact")
//    private PasienModel pasien;

    //SETTER AND GETTER


    public Long getIdContact() {
        return idContact;
    }

    public void setIdContact(Long idContact) {
        this.idContact = idContact;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getNoHp() {
        return noHp;
    }


    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }

    //CONSTRUCTOR


    public EmergencyContactModel() {
        this.nama = "default";
        this.nik = "default";
        this.noHp = "00000";
    }

    public EmergencyContactModel(@NotNull @Size(max = 20) String nama, @NotNull @Size(max = 255) String nik, @NotNull @Size(max = 20) String noHp) {
        this.nama = nama;
        this.nik = nik;
        this.noHp = noHp;
    }
}
