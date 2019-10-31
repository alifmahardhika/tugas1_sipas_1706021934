package tugas1.sipas.service;

import tugas1.sipas.model.EmergencyContactModel;
import tugas1.sipas.model.PasienAsuransiModel;
import tugas1.sipas.model.PasienModel;
import tugas1.sipas.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EmergencyServiceImpl implements  EmergencyService{
    @Autowired
    private EmergencyContactDB emergencyContactDB;

    @Override
    public List<EmergencyContactModel> getContactList(){
        List<EmergencyContactModel> list = emergencyContactDB.findAll();
        return list;
    };
}
