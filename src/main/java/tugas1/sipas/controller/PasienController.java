package tugas1.sipas.controller;

//import apap.tutorial.gopud.model.MenuModel;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import tugas1.sipas.model.AsuransiModel;
import tugas1.sipas.model.PasienModel;
//import apap.tutorial.gopud.service.MenuService;
import tugas1.sipas.service.AsuransiService;
import tugas1.sipas.service.AsuransiServiceImpl;
import tugas1.sipas.service.PasienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class PasienController {
    @Qualifier("pasienServiceImpl")
    @Autowired
    private PasienService pasienService;

    @Autowired
    private AsuransiService asuransiService;

//    @Autowired
//    private MenuService menuService;

    @RequestMapping("/")
    public String beranda() {
        return "beranda";
    }

    @RequestMapping(value = "/pasien/tambah", method = RequestMethod.GET)
    public String addPasienFormPage(Model model) {
        PasienModel newPasien = new PasienModel();
        List<AsuransiModel> listAsuransi = asuransiService.getListAsuransi();
        model.addAttribute("pasien", newPasien);
        model.addAttribute("listAsuransi", listAsuransi);
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




    @Override
    protected void initBinder(PortletRequest request, PortletRequestDataBinder binder) throws Exception {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        df.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(df, true));

        super.initBinder(request,binder);
    }

}
