package tugas1.sipas.controller;

import org.dom4j.rule.Mode;
import org.springframework.web.bind.annotation.*;
import tugas1.sipas.model.*;
import tugas1.sipas.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import java.util.List;
import java.util.Optional;

@Controller
public class AsuransiController {
    @Qualifier("asuransiServiceImpl")
    @Autowired
    private AsuransiService asuransiService;

    @Qualifier("pasienServiceImpl")
    @Autowired
    private PasienService pasienService;

    @RequestMapping(value = "/asuransi/tambah", method = RequestMethod.GET)
    public String tambahAsuransi(Model asuransi) {
        AsuransiModel newAsuransi = new AsuransiModel();
        asuransi.addAttribute("newAsuransi", newAsuransi);
        return "form-tambah-asuransi";
    }

    @RequestMapping(value = "/asuransi/tambah", method = RequestMethod.POST)
    public String tambahAsuransiSubmit(@ModelAttribute AsuransiModel asuransi, Model model) {
        asuransiService.tambahAsuransi(asuransi);
        model.addAttribute("nama", asuransi.getNama());
        return "tambah-Asuransi";


//    @RequestMapping(value = "/asuransi", method = RequestMethod.GET)
//    public String viewAll(Model model) {
//        List<AsuransiModel> allAsuransi = asuransiService.getListAsuransi();
//        model.addAttribute("asuransiList", allAsuransi);
//        return "viewasuransi";
//    }

//    @RequestMapping(path = "/asuransi/view/{idAsuransi}", method = RequestMethod.GET)
//    public String viewAsuransi(
//            @PathVariable Long idAsuransi, Model model
//    ) {
//        AsuransiModel asuransi = asuransiService.getAsuransiByIdAsuransi(idAsuransi).get();
//        model.addAttribute("asuransiDetil", asuransi);
//        return "asuransidetil";
//    }


//
//
//    @RequestMapping(value = "/pasien/delete/{idPasien}", method = RequestMethod.GET)
//    public String hapusAsuransiWithId(@PathVariable Long idAsuransi, @ModelAttribute AsuransiModel asuransi, Model model) {
//
//        AsuransiModel asuransiku = asuransiService.getAsuransiByIdAsuransi(idAsuransi).get();
//        List<PasienModel> obat = pasienService.getPasienList();
//
//        if (obat.size() == 0) {
//            model.addAttribute("obat", obat);
//            asuransiService.hapusAsuransi(idAsuransi);
//            return "hapus-asuransi";
//        }
//        return "delete-asuransi-fail";
//
//
    }
}
