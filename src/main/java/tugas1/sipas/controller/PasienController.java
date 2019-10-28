package tugas1.sipas.controller;

//import apap.tutorial.gopud.model.MenuModel;
import tugas1.sipas.model.PasienModel;
//import apap.tutorial.gopud.service.MenuService;
import tugas1.sipas.service.PasienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
public class PasienController {
    @Qualifier("pasienServiceImpl")
    @Autowired
    private PasienService pasienService;

//    @Autowired
//    private MenuService menuService;

    @RequestMapping("/")
    public String beranda() {
        return "beranda";
    }

    @RequestMapping(value = "/pasien/tambah", method = RequestMethod.GET)
    public String addPasienFormPage(Model model) {
        PasienModel newPasien = new PasienModel();
        model.addAttribute("pasien", newPasien);
        return "form-add-pasien";
    }

    @RequestMapping(value = "pasien/view-all", method = RequestMethod.GET)
    public String viewAll(Model model) {
        List<PasienModel> allPasien = pasienService.getPasienList();
        model.addAttribute("pasienList", allPasien);
        return "viewall-pasien";
    }

    @RequestMapping(path = "/pasien/tambah", method = RequestMethod.POST)
    public String addPasienSubmit(@ModelAttribute PasienModel pasien, Model model) {
        pasienService.addPasien(pasien);
        model.addAttribute("namaPasien", pasien.getNama());
        return "add-pasien";
    }


    protected void referenceData(HttpServletRequest request) throws Exception {
        Map referenceData = new HashMap();
        Map<String, Integer> jk = new LinkedHashMap<String, Integer>();
        jk.put("Laki-laki", 1);
        jk.put("Perempuan", 2);
        jk.put("Lainnya", 3);
        referenceData.put("jenisKelamin", jk);
    }

}
