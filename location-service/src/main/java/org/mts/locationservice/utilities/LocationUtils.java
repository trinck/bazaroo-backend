package org.mts.locationservice.utilities;
import org.mts.locationservice.entities.GeoZone;

public interface LocationUtils {


    public Double distanceBetweenKlm(GeoZone zoneA, GeoZone zoneB);

    public boolean isInRadiusKlm(GeoZone center, Double radius, GeoZone toCheck);


}
