package org.mts.locationservice.repositories;

import org.mts.locationservice.entities.GeoZone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeoZoneRepository extends JpaRepository<GeoZone,Long> {

    public Page<GeoZone> findAllByNameContains(String name, Pageable pageable);
}
