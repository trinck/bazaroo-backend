package org.mts.locationservice.repositories;

import org.mts.locationservice.entities.GeoPoint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeoPointRepository extends JpaRepository<GeoPoint, Long> {
}