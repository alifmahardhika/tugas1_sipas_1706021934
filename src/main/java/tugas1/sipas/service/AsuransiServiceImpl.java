package tugas1.sipas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tugas1.sipas.repository.*;
import tugas1.sipas.model.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AsuransiServiceImpl implements AsuransiService {
    @Autowired
    private AsuransiDB asuransiDB;

    @Override
    public List<AsuransiModel> getListAsuransi() {
        return asuransiDB.findAll();
    }

    @Override
    public Optional<AsuransiModel> getAsuransiByIdAsuransi(Long idAsuransi) {
        return asuransiDB.findAsuransiByIdAsuransi(idAsuransi);
    }

    @Override
    public void tambahAsuransi(AsuransiModel asuransi) {
        asuransiDB.save(asuransi);
    }

    @Override
    public void hapusAsuransi(Long idAsuransi) {
        asuransiDB.deleteById(idAsuransi);
    }
}
