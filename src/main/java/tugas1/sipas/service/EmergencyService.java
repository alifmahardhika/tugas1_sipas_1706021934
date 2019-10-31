package tugas1.sipas.service;

import tugas1.sipas.model.EmergencyContactModel;
import java.util.List;
import java.util.Optional;


import java.util.List;
import java.util.Optional;

public interface EmergencyService {
    List<EmergencyContactModel> getContactList();
    void removeEmergencyContact(Long id);
    EmergencyContactModel changeContact(EmergencyContactModel contact);

}

