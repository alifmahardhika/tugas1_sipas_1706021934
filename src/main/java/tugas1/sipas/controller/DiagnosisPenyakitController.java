package tugas1.sipas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tugas1.sipas.model.*;
import tugas1.sipas.service.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class DiagnosisPenyakitController {
    @Qualifier("diagnosisPenyakitServiceImpl")
    @Autowired
    private DiagnosisPenyakitService diagnosisPenyakitService;

    @RequestMapping("/diagnosis-penyakit-all")
    public String beranda(Model model) {
        List<DiagnosisPenyakitModel> listDiagnosis = diagnosisPenyakitService.getListDiagnosis();
        model.addAttribute("listDiagnosis", listDiagnosis);

        return "viewall-diagnosis";
    }

    @RequestMapping(value = "/diagnosis-penyakit/tambah", method = RequestMethod.GET)
    public String tambahDiagnosis(Model diagnosis) {
        DiagnosisPenyakitModel newDiag = new DiagnosisPenyakitModel();
        diagnosis.addAttribute("newDiagnosis", newDiag);
        return "form-tambah-diagnosis";
    }

    @RequestMapping(value = "/diagnosis-penyakit/tambah", method = RequestMethod.POST)
    public String tambahDiagSubmit(@ModelAttribute DiagnosisPenyakitModel diagnosis, Model model) {
        diagnosisPenyakitService.tambahDiagnosisPenyakit(diagnosis);
        model.addAttribute("nama", diagnosis.getNamaDiagnosis());
        return "tambah-diagnosis";
    }

    @RequestMapping(path = "/diagnosis-penyakit", method = RequestMethod.GET)
    public String view(
            @RequestParam(value = "idDiagnosis") String idStr, Model model
    ) {
        List<DiagnosisPenyakitModel> listDiag = diagnosisPenyakitService.getListDiagnosis();
        DiagnosisPenyakitModel target = null;
        for (DiagnosisPenyakitModel diag:listDiag){
            if (diag.getIdPenyakit() == Long.valueOf(idStr)){
                target = diag;
            }
        }
        model.addAttribute("diagnosis", target);

        //return view template
        return "view-diagnosis";
    }


    @RequestMapping(value = "/diagnosis-penyakit/hapus/{idDiagnosis}")
    public String deleteDiagnosisWithId(@PathVariable(value = "idDiagnosis") Long idDiagnosis, Model model) {
//        List<EmergencyContactModel> listContact = emergencyService.getContactList();
        try{
//            PasienModel pasienNyaNih = null;
//            Long idPasienBeneran= Long.valueOf("0");

            List<DiagnosisPenyakitModel> allDiag = diagnosisPenyakitService.getListDiagnosis();
            DiagnosisPenyakitModel diagTarget = null;
            for (DiagnosisPenyakitModel diag : allDiag){
                if(Long.valueOf(diag.getIdPenyakit()) == idDiagnosis){
                    diagTarget = diag;
                }
            }
            String namaPenyakit = diagTarget.getNamaDiagnosis();
            diagnosisPenyakitService.hapusDiagnosisPenyakit(diagTarget.getIdPenyakit());
            model.addAttribute("nama", namaPenyakit );

            return "diagnosis-dihapus";

//            List<PasienAsuransiModel> listAsuransiPasien = pasienService.findAsuransiPasien();
////        model.addAttribute("pasienList", allPasien);
//
//            if (listAsuransiPasien.size() == 0){
//                pasienService.removePasien(idPasienBeneran);
//                return "pasien-dihapus";
//            }
//
//
//            else {
//                String[] listAsuransiPasienSplit = pasienNyaNih.getIdAsuransi().split(",");
//                for (int i = 0; i<listAsuransiPasienSplit.length; i++){
//                    for (PasienAsuransiModel relasi:listAsuransiPasien){
//                        String idPasienDiRelasi = "" + relasi.getIdPasien();
//                        if (idPasienDiRelasi.equalsIgnoreCase(Long.toString(idPasienLong))){
//                            pasienService.removeRelasi(Long.valueOf(relasi.getIdRelasi()));
//                        }
//                    }
//                }
//                pasienService.removePasien(idPasienBeneran);
//                emergencyService.removeEmergencyContact(Long.valueOf(pasienNyaNih.getIdEmergencyContact()));
//                return "pasien-dihapus";
//            }
        }
        catch (Exception e) {
            return "pasien-tidak-ditemukan";
        }
    }

}
