package org.mts.announcesservice.service;

import org.mts.announcesservice.entities.Preference;

public interface IPreferenceService {

    public Preference save(Preference preference);
    public Preference getPreferenceByUserId(String userId);
    public Preference deleteByUserId(String userId);
}
