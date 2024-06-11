package org.mts.locationservice.services;

import org.mts.locationservice.entities.GeoZone;
import org.mts.locationservice.repositories.GeoZoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class GeoZoneService implements IGeoZoneService{

    @Autowired
   private GeoZoneRepository geoZoneRepository;

    /**
     * @return
     */
    @Override
    public List<GeoZone> getZones() {

        return this.geoZoneRepository.findAll();
    }

    /**
     * @param pageable
     * @return
     */
    @Override
    public Page<GeoZone> getZonesPages(Pageable pageable) {

        return this.geoZoneRepository.findAll(pageable);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public GeoZone getZoneById(String id) {

        return this.geoZoneRepository.findById(id).orElse(null);
    }

    /**
     * @param zone
     * @return
     */
    @Override
    public GeoZone addZone(GeoZone zone) {
        return this.geoZoneRepository.save(zone);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public GeoZone deleteZoneById(String id) {
        GeoZone zone = this.geoZoneRepository.findById(id).orElseThrow();
        this.geoZoneRepository.deleteById(id);
        return zone;
    }

    /**
     * @param geoZone
     * @return
     */
    @Override
    public GeoZone save(GeoZone geoZone) {
        return this.geoZoneRepository.save(geoZone);
    }

    /**
     * @param geoZone
     * @return
     */
    @Override
    public GeoZone update(GeoZone geoZone) {
        return this.geoZoneRepository.save(geoZone);
    }

    /**
     * @param name
     * @return
     */
    @Override
    public Page<GeoZone> getZoneByName(String name, Pageable pageable) {
        return this.geoZoneRepository.findAllByNameContains(name, pageable);
    }


}
