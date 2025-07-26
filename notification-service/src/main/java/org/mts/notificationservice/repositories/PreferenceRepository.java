package org.mts.notificationservice.repositories;

import org.mts.notificationservice.entities.Preference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PreferenceRepository extends JpaRepository<Preference, Long> {
  Optional<Preference> findPreferenceByUserId(String userId);
}