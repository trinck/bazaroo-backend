package org.mts.usersservice.services;

import org.mts.usersservice.entities.Preference;
import org.mts.usersservice.repositories.PreferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class PreferenceService implements IPreferenceService{

    @Autowired
   private PreferenceRepository preferenceRepository;

    /**
     * @param preference
     * @return
     */
    @Override
    public Preference creatPreference(Preference preference) {
        return this.preferenceRepository.save(preference);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Preference getPreferenceById(Long id) {
        return this.preferenceRepository.findById(id).orElseThrow();
    }

    /**
     * @param preference
     * @return
     */
    @Override
    public Preference updatePreference(Preference preference) {
        return this.preferenceRepository.save(preference);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Preference deletePreferenceById(Long id) {

        Preference preference = this.preferenceRepository.findById(id).orElseThrow();
        this.preferenceRepository.deleteById(id);
        return preference;
    }

    /**
     * @param pageable
     * @return
     */
    @Override
    public Page<Preference> getPreferences(Pageable pageable) {
        return this.preferenceRepository.findAll(pageable);
    }

    /**
     * @return
     */
    @Override
    public List<Preference> getListPreferences() {
        return this.preferenceRepository.findAll();
    }
}
