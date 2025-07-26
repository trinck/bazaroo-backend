package org.mts.announcesservice.repositories;

import org.mts.announcesservice.entities.Preference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PreferenceRepository extends JpaRepository<Preference, Long> {
    Optional<Preference> findByUserId(String userId);
}