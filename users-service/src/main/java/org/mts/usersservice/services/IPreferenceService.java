package org.mts.usersservice.services;

import org.mts.usersservice.entities.Preference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IPreferenceService {


    List<String> getFavorites(String userId);

    void addFavorite(String userId, String adId);

    void removeFavorite(String userId, String adId);

    public Preference createPreference(Preference preference);
    public Preference getPreferenceById(Long id);
    public Preference getPreferenceByUserId(String  userId);
    public Preference deletePreferenceByUserId(String  userId);
    public Preference updatePreference(Preference preference);
    public Preference deletePreferenceById(Long id);
    public Page<Preference> getPreferences(Pageable pageable);
    public List<Preference> getListPreferences();

}
