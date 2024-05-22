package org.mts.usersservice.services;

import org.mts.usersservice.entities.Preference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IPreferenceService {



    public Preference creatPreference(Preference preference);
    public Preference getPreferenceById(Long id);
    public Preference updatePreference(Preference preference);
    public Preference deletePreferenceById(Long id);
    public Page<Preference> getPreferences(Pageable pageable);
    public List<Preference> getListPreferences();

}
