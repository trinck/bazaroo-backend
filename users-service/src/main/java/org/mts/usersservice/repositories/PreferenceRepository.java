package org.mts.usersservice.repositories;

import org.mts.usersservice.entities.Preference;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PreferenceRepository extends JpaRepository<Preference, Long> {
}
