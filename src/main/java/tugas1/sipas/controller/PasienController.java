package tugas1.sipas.controller;

import tugas1.sipas.model.*;
import tugas1.sipas.service.AsuransiService;
import tugas1.sipas.service.AsuransiServiceImpl;
import tugas1.sipas.service.EmergencyService;
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

    @Autowired
    private EmergencyService emergencyService;

//    @Autowired
//    private MenuService menuService;

    @RequestMapping("/")
    public String beranda(Model model) {
        List<PasienModel> listPasien = pasienService.getPasienList();
        List<EmergencyContactModel> listContact = emergencyService.getContactList();
        model.addAttribute("listPasien", listPasien);
        model.addAttribute("listContact", listContact);

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
//
//    @RequestMapping(path = "/pasien/tambah", method = RequestMethod.POST)


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

        //Save ke database
        pasienService.addPasien(pasien);

        //set Asuransi
        String[] listAsuransiPasien = pasien.getIdAsuransi().split(",");
        List<AsuransiModel> daftar = new ArrayList<>();
        for (int i=0; i<listAsuransiPasien.length; i++){
            for (AsuransiModel ass: asuransiService.getListAsuransi()){
                if (listAsuransiPasien[i].equalsIgnoreCase(Long.toString(ass.getIdAsuransi()))){
                    daftar.add(ass);
                    PasienAsuransiModel relasiAsuransi = new PasienAsuransiModel(BigInteger.valueOf(pasien.getIdPasien()),BigInteger.valueOf(ass.getIdAsuransi()));
                    pasienService.addAsuransiRelation(relasiAsuransi);
                }
            }
        }

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

    @RequestMapping(value = "/pasien/hapus/{idPasien}")
    public String deletePasienWithId(@PathVariable(value = "idPasien") Long idPasien,  Model model) {
        try{
            PasienModel pasienNyaNih = null;
            Long idPasienBeneran= Long.valueOf("0");

            List<PasienModel> allPasien = pasienService.getPasienList();
            Long idPasienLong = Long.valueOf(idPasien);
            for (PasienModel pasienModel : allPasien){
                if(Long.valueOf(pasienModel.getIdPasien()) == idPasienLong){
                    idPasienBeneran = pasienModel.getIdPasien();
                    pasienNyaNih = pasienModel;
                }
            }

            List<PasienAsuransiModel> listAsuransiPasien = pasienService.findAsuransiPasien();
//        model.addAttribute("pasienList", allPasien);

            if (listAsuransiPasien.size() == 0){
                System.out.println("=================================1");
                pasienService.removePasien(idPasienBeneran);
                return "pasien-dihapus";
            }


            else {
                String[] listAsuransiPasienSplit = pasienNyaNih.getIdAsuransi().split(",");
                for (int i = 0; i<listAsuransiPasienSplit.length; i++){
                    for (PasienAsuransiModel relasi:listAsuransiPasien){
                        String idPasienDiRelasi = "" + relasi.getIdPasien();
                        if (idPasienDiRelasi.equalsIgnoreCase(Long.toString(idPasienLong))){
                            System.out.println("=================================2 " + relasi.getIdPasien());
                            pasienService.removeRelasi(Long.valueOf(relasi.getIdRelasi()));
                        }
                        System.out.println("=================================2a " + relasi.getIdPasien());

                    }
                    System.out.println("=================================3");
                }
                pasienService.removePasien(idPasienBeneran);
                return "pasien-dihapus";
            }
        }
        catch (Exception e) {
            return "pasien-tidak-ditemukan";
        }
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
        int range = 51;
        for (int i = 0; i < 2; i++) {
            int rand = (int) (Math.random() * range);
            digit += temp[rand];
        }
        return digit;
    }
}
