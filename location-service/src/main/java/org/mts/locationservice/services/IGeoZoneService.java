package org.mts.locationservice.services;

import org.mts.locationservice.entities.GeoZone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IGeoZoneService {

    public List<GeoZone> getZones();
    public Page<GeoZone> getZonesPages(Pageable pageable);
    public GeoZone getZoneById(String id);
    public GeoZone addZone(GeoZone zone);
    public GeoZone deleteZoneById(String id);
    public GeoZone save(GeoZone geoZone);
    public GeoZone update(GeoZone geoZone);
    public Page<GeoZone> getZoneByName(String name, Pageable pageable);
}
