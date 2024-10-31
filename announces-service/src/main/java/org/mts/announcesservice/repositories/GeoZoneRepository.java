package org.mts.announcesservice.repositories;

import org.mts.announcesservice.entities.GeoZone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeoZoneRepository extends JpaRepository<GeoZone, String> {
}
