package tugas1.sipas.service;
import tugas1.sipas.model.AsuransiModel;

import java.util.List;
import java.util.Optional;

public interface AsuransiService {
    List<AsuransiModel> getListAsuransi();
    Optional<AsuransiModel> getAsuransiByIdAsuransi(Long idAsuransi);
    void tambahAsuransi(AsuransiModel asuransi);
    void hapusAsuransi(Long idAsuransi);
}
