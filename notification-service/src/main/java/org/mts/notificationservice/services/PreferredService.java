package org.mts.notificationservice.services;

import org.mts.notificationservice.entities.Preference;
import org.mts.notificationservice.repositories.PreferenceRepository;
import org.springframework.stereotype.Service;


@Service
public class PreferredService implements IPreferredService {


    private PreferenceRepository preferenceRepository;

    public PreferredService(PreferenceRepository preferenceRepository) {
        this.preferenceRepository = preferenceRepository;
    }

    /**
     * @param userId
     * @return
     */
    @Override
    public Preference findByUserId(String userId) {
        return this.preferenceRepository.findPreferenceByUserId(userId).orElseThrow();
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Preference findById(Long id) {
        return this.preferenceRepository.findById(id).orElseThrow();
    }

    /**
     * @param preference
     * @return
     */
    @Override
    public Preference save(Preference preference) {
        return this.preferenceRepository.save(preference);
    }
}
