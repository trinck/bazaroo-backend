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


   private final PreferenceRepository preferenceRepository;

    public PreferenceService(PreferenceRepository preferenceRepository) {
        this.preferenceRepository = preferenceRepository;
    }

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
     * @param userId
     * @return
     */
    @Override
    public Preference getPreferenceByUserId(String userId) {
        return this.preferenceRepository.findByUserId(userId).orElseThrow();
    }

    /**
     * @param userId
     * @return
     */
    @Override
    public Preference deletePreferenceByUserId(String userId) {
        Preference preference = this.preferenceRepository.findByUserId(userId).orElseThrow();
        this.preferenceRepository.deleteByUserId(userId);
        return preference;
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
