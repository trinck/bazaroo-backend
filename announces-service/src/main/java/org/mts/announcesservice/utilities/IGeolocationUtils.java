package org.mts.announcesservice.utilities;

import org.mts.announcesservice.remote_entities.GeoZone;

public interface IGeolocationUtils {

    public Double distanceBetweenKm(GeoZone zoneA, GeoZone zoneB);

    public boolean isInRadiusKm(GeoZone center, Double radius, GeoZone toCheck);
}
