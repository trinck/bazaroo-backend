package org.mts.locationservice.utilities;
import org.mts.locationservice.entities.GeoZone;

public interface LocationUtils {


    public Double distanceBetweenKm(GeoZone zoneA, GeoZone zoneB);

    public boolean isInRadiusKm(GeoZone center, Double radius, GeoZone toCheck);


}
