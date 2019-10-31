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
    }
}
