package org.mts.announcesservice.remote_entities;



import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mts.announcesservice.dtos.GeoZoneInputDTO;
import org.mts.announcesservice.enums.AdTrackingType;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrackingEventDocument implements Serializable {


    private String id;

    private String adId;//announce id
    private String ipAddress; //User's Address IP
    private String userAgent;
    private AdTrackingType eventType;
    private Date timestamp;

    private String userId;
    private GeoZoneInputDTO geoLocation;

}
