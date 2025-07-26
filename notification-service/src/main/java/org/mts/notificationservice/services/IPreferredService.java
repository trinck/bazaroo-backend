package org.mts.notificationservice.services;


import org.mts.notificationservice.entities.Preference;

public interface IPreferredService {

    public Preference findByUserId(String userId);
    public Preference findById(Long id);
    public Preference save(Preference preference);
}
