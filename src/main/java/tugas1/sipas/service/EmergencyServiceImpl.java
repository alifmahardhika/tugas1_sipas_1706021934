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
    }

    @Override
    public void removeEmergencyContact(Long id) {emergencyContactDB.deleteById(id);}

    @Override
    public EmergencyContactModel changeContact(EmergencyContactModel contact) {
        EmergencyContactModel targetContact = emergencyContactDB.findById(contact.getIdContact()).get();

        try{
            targetContact.setNama(contact.getNama());
            targetContact.setNik((contact.getNik()));
            targetContact.setNoHp(contact.getNoHp());
            emergencyContactDB.save(contact);
            return targetContact;
        } catch (NullPointerException nullException){
            return null;
        }
    }
}
