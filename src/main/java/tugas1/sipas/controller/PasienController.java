package tugas1.sipas.controller;

//import apap.tutorial.gopud.model.MenuModel;
import tugas1.sipas.model.AsuransiModel;
import tugas1.sipas.model.EmergencyContactModel;
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
import java.math.BigInteger;
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
        List<PasienModel> listPasien = pasienService.getPasienList();
        return "beranda";
    }

    @RequestMapping(value = "/pasien/tambah", method = RequestMethod.GET)
    public String addPasienFormPage(Model model) {
        PasienModel newPasien = new PasienModel();
        List<AsuransiModel> listAsuransi = asuransiService.getListAsuransi();
        model.addAttribute("pasien", newPasien);
        model.addAttribute("listAsuransi", listAsuransi);
        model.addAttribute("emergencyContact", new EmergencyContactModel());
        return "form-add-pasien";
    }


    @RequestMapping(path = "/pasien/tambah", method = RequestMethod.POST)
    public String addPasienSubmit(@ModelAttribute PasienModel pasien, Model model, @ModelAttribute EmergencyContactModel emergencyContact) {
        //generate unique code
        String currYear = getCurrentYearStr();
        String birthDateStr = getSplitTanggalLahir(pasien.getTanggalLahir());
        String randomTwo = makeRandomTwoDigits();
        String uniqueCode = currYear + birthDateStr + pasien.getJenisKelamin() + randomTwo;
        pasien.setUniqueCode((uniqueCode));
        //set no emergencyContact
        String[] foo = pasien.getIdEmergencyContact().split(",");
        EmergencyContactModel contact = new EmergencyContactModel(foo[0], foo[1],foo[2]);
        pasienService.addEmergencyContact(contact);
        pasien.setIdEmergencyContact(Long.toString(contact.getIdContact()));


        //save ke database
        pasienService.addPasien(pasien);
        model.addAttribute("namaPasien", pasien.getNama());
        model.addAttribute("uniqueCode", pasien.getUniqueCode());
        return "add-pasien";
    }

    @RequestMapping(value = "pasien/view-all", method = RequestMethod.GET)
    public String viewAll(Model model) {
        List<PasienModel> allPasien = pasienService.getPasienList();
        model.addAttribute("pasienList", allPasien);
        return "viewall-pasien";
    }

    //METHOD LAIN-LAIN
    protected String getCurrentYearStr() {
        return "" + (Calendar.getInstance().get(Calendar.YEAR) + 5 );
    }

    static protected String getSplitTanggalLahir(String tanggal){
        String temp = "";
        String[] arr = tanggal.split("-");
        arr[0] = arr[0].substring(0, 2);
        for (int i = 2; i>=0; i--){temp = temp + arr[i];}
        return  temp;
    }

    static public String makeRandomTwoDigits() {
        String digit ="";
        String sourceStr= "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        String[] temp= sourceStr.split("");
        int range = 61;
        for (int i = 0; i < 2; i++) {
            int rand = (int) (Math.random() * range);
            digit += temp[rand];
        }
        return digit;
    }





}
