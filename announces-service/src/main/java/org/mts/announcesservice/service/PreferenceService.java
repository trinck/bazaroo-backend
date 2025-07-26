package org.mts.announcesservice.service;

import org.mts.announcesservice.entities.Preference;
import org.mts.announcesservice.repositories.PreferenceRepository;
import org.springframework.stereotype.Service;


@Service
public class PreferenceService implements IPreferenceService {

   private final PreferenceRepository preferenceRepository;

    public PreferenceService(PreferenceRepository preferenceRepository) {
        this.preferenceRepository = preferenceRepository;
    }

    /**
     * @param preference
     * @return
     */
    @Override
    public Preference save(Preference preference) {
        return this.preferenceRepository.save(preference);
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
    public Preference deleteByUserId(String userId) {
        Preference preference = this.getPreferenceByUserId(userId);
        this.preferenceRepository.delete(preference);
        return preference;
    }


}
