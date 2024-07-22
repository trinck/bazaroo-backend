package org.mts.announcesservice.utilities;

import org.mts.announcesservice.remote_entities.GeoZone;

public class GeolocationImp implements IGeolocationUtils{
    /**
     * @param zoneA
     * @param zoneB
     * @return
     */
    @Override
    public Double distanceBetweenKm(GeoZone zoneA, GeoZone zoneB) {
        return 0.0;
    }

    /**
     * @param center
     * @param radius
     * @param toCheck
     * @return
     */
    @Override
    public boolean isInRadiusKm(GeoZone center, Double radius, GeoZone toCheck) {
        return false;
    }
}
