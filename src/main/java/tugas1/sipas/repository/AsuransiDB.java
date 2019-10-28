package tugas1.sipas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tugas1.sipas.model.AsuransiModel;
import java.util.List;
import java.util.Optional;

public interface AsuransiDB  extends JpaRepository<AsuransiModel, Long> {
    List<AsuransiModel> findByIdAsuransi(Long idAsuransi);
    Optional<AsuransiModel> findAsuransiByIdAsuransi(Long idAsuransi);
}

