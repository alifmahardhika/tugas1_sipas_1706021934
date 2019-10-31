package tugas1.sipas.controller;

import tugas1.sipas.model.*;
import tugas1.sipas.service.*;
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

    @Autowired
    private DiagnosisPenyakitService diagnosisPenyakitService;



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
        List<EmergencyContactModel> listContact = emergencyService.getContactList();
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
                pasienService.removePasien(idPasienBeneran);
                return "pasien-dihapus";
            }


            else {
                String[] listAsuransiPasienSplit = pasienNyaNih.getIdAsuransi().split(",");
                for (int i = 0; i<listAsuransiPasienSplit.length; i++){
                    for (PasienAsuransiModel relasi:listAsuransiPasien){
                        String idPasienDiRelasi = "" + relasi.getIdPasien();
                        if (idPasienDiRelasi.equalsIgnoreCase(Long.toString(idPasienLong))){
                            pasienService.removeRelasi(Long.valueOf(relasi.getIdRelasi()));
                        }
                    }
                }
                pasienService.removePasien(idPasienBeneran);
                emergencyService.removeEmergencyContact(Long.valueOf(pasienNyaNih.getIdEmergencyContact()));
                return "pasien-dihapus";
            }
        }
        catch (Exception e) {
            return "pasien-tidak-ditemukan";
        }
    }

    @RequestMapping(path = "/pasien", method = RequestMethod.GET)
    public String view(
            @RequestParam(value = "nik") String nik, Model model
    ) {
        List<PasienModel> allPasien = pasienService.getPasienList();
        PasienModel targetPasien=null;
        for (PasienModel pasienModel : allPasien){
            if (pasienModel.getNik().equalsIgnoreCase(nik)){
                targetPasien = pasienModel;
            }
        }

        List<EmergencyContactModel> contactList = emergencyService.getContactList();
        EmergencyContactModel contactTarget = targetPasien.getEmergencyContact(contactList);

        List<Long> daftarIdAsuransiPasien = new ArrayList<>();
        List<AsuransiModel> daftarAsuransiPasien = new ArrayList<>();
        List<PasienAsuransiModel> listAsuransiPasien = pasienService.findAsuransiPasien();
        String[] listAsuransiPasienSplit = targetPasien.getIdAsuransi().split(",");

        //ambil id asuransi dari relasi
        for (PasienAsuransiModel relasi:listAsuransiPasien){
            String idPasienDiRelasi = "" + relasi.getIdPasien();
            if (idPasienDiRelasi.equalsIgnoreCase(Long.toString(targetPasien.getIdPasien()))){
                daftarIdAsuransiPasien.add(Long.valueOf("" + (relasi.getIdAsuransi())));
            }
        }
        //isi list asuransi pasien
        for (int i = 0; i<daftarIdAsuransiPasien.size(); i++){
            for (int n = 0; n<asuransiService.getListAsuransi().size(); n++){
                if (daftarIdAsuransiPasien.get(i) == asuransiService.getListAsuransi().get(n).getIdAsuransi()){
                    daftarAsuransiPasien.add(asuransiService.getListAsuransi().get(n));
                }
            }
        }

        model.addAttribute("pasien", targetPasien);
        model.addAttribute("emergencyContact", contactTarget);
        model.addAttribute("listAsuransi", daftarAsuransiPasien);



        //return view template
        return "view-pasien";
    }

    @RequestMapping(value = "/pasien/ubah/{nikPasien}", method = RequestMethod.GET)
    public String changePasienFormPage(@PathVariable String nikPasien, Model model) {


        List<PasienModel> allPasien = pasienService.getPasienList();
        PasienModel targetPasien=null;
        for (PasienModel pasienModel : allPasien){
            if (pasienModel.getNik().equalsIgnoreCase(nikPasien)){
                targetPasien = pasienModel;
            }
        }
        model.addAttribute("pasien", targetPasien);
        List<EmergencyContactModel> listContact = emergencyService.getContactList();
        EmergencyContactModel contactPasien = targetPasien.getEmergencyContact(listContact);
        model.addAttribute("contactPasien", contactPasien);

        return "form-change-pasien";
    }




    @RequestMapping(value = "/pasien/ubah/{nikPasien}", method = RequestMethod.POST)
    public String changePasienFormSubmit(@PathVariable String nikPasien, @ModelAttribute("pasien") PasienModel pasien, @ModelAttribute("contactPasien") EmergencyContactModel contactPasien, Model model) throws NullPointerException{

        PasienModel newPasien = pasienService.changePasien(pasien);

        //generate unique code
        String currYear = getCurrentYearStr();
        String birthDateStr = getSplitTanggalLahir(newPasien.getTanggalLahir());
        String randomTwo = makeRandomTwoDigits();
        String uniqueCode = currYear + birthDateStr + newPasien.getJenisKelamin() + randomTwo;
        newPasien.setUniqueCode((uniqueCode));
        newPasien = pasienService.changePasien(newPasien);


//        contactPasien = emergencyService.changeContact(contactPasien);
        model.addAttribute("namaPasien", newPasien.getNama());
        model.addAttribute("uniqueCode", newPasien.getUniqueCode());
        return "change-pasien";
    }


    @RequestMapping(value = "/pasien/ubah/emergencyContact/{idContact}", method = RequestMethod.GET)
    public String changePasienContactForm(@PathVariable String idContact, Model model) {
        List<EmergencyContactModel> listContact = emergencyService.getContactList();
        EmergencyContactModel targetContact = null;
        Long idLong = Long.valueOf((idContact));
        for (EmergencyContactModel contact:listContact){
            if(contact.getIdContact() == idLong){
                targetContact = contact;
            }
        }

        model.addAttribute("contactPasien", targetContact);
        System.out.println(targetContact.getIdContact()+"===================a");

        return "form-change-contact";
    }




    @RequestMapping(value = "/pasien/ubah/emergencyContact/{idContact}", method = RequestMethod.POST)
    public String changePasienContact(@PathVariable String idContact, @ModelAttribute("contactPasien") EmergencyContactModel contactPasien, Model model) throws NullPointerException{

        System.out.println(contactPasien.getIdContact()+"===================");
        EmergencyContactModel contactBaru = emergencyService.changeContact(contactPasien);
        return "change-contact";
    }

    @RequestMapping(value = "/pasien/{nikPasien}/tambah-diagnosis", method = RequestMethod.GET)
    public String tambahDiagnosisForm(@PathVariable String nikPasien, Model model) {


        List<PasienModel> allPasien = pasienService.getPasienList();
        PasienModel targetPasien=null;
        for (PasienModel pasienModel : allPasien){
            if (pasienModel.getNik().equalsIgnoreCase(nikPasien)){
                targetPasien = pasienModel;
            }
        }
        model.addAttribute("pasien", targetPasien);
        List<EmergencyContactModel> listContact = emergencyService.getContactList();
        EmergencyContactModel contactPasien = targetPasien.getEmergencyContact(listContact);
        model.addAttribute("emergencyContact", contactPasien);

        List<DiagnosisPasienModel> listDiagnosisPasien = pasienService.findDiagnosisPasien();

        model.addAttribute("listDiagnosisPasien", listDiagnosisPasien);

        List<DiagnosisPenyakitModel> listDiagnosisPenyakit = diagnosisPenyakitService.getListDiagnosis();

        model.addAttribute("listDiagnosis", listDiagnosisPenyakit);
        return "pasie-tambah-diagnosis";
    }




    @RequestMapping(value = "/pasien/{nikPasien}/tambah-diagnosis", method = RequestMethod.POST)
    public String tambahDiagPost(@PathVariable String nikPasien, @ModelAttribute("pasien.idPasien") String idPasienStr, @ModelAttribute("pasien") PasienModel pasien, @ModelAttribute("diagnosisSakit.idDiagnosis") String idDiagnosisStr, Model model) throws NullPointerException{
        Long idPasien = Long.valueOf(idPasienStr);
        Long idDiagnosis = Long.valueOf(idDiagnosisStr);
        DiagnosisPasienModel diagnosisPasien = new DiagnosisPasienModel(idPasien,idDiagnosis);
        pasienService.addDiagnosisPasien(diagnosisPasien);



        /////////////////
        List<PasienModel> allPasien = pasienService.getPasienList();
        PasienModel targetPasien=null;
        for (PasienModel pasienModel : allPasien){
            if (pasienModel.getNik().equalsIgnoreCase(nikPasien)){
                targetPasien = pasienModel;
            }
        }
        model.addAttribute("pasien", targetPasien);
        List<EmergencyContactModel> listContact = emergencyService.getContactList();
        EmergencyContactModel contactPasien = targetPasien.getEmergencyContact(listContact);
        model.addAttribute("emergencyContact", contactPasien);

        List<DiagnosisPasienModel> listDiagnosisPasien = pasienService.findDiagnosisPasien();

        model.addAttribute("listDiagnosisPasien", listDiagnosisPasien);

        List<DiagnosisPenyakitModel> listDiagnosisPenyakit = diagnosisPenyakitService.getListDiagnosis();

        model.addAttribute("listDiagnosis", listDiagnosisPenyakit);

        return "pasie-tambah-diagnosis";
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
