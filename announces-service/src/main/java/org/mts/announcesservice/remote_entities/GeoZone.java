package org.mts.announcesservice.remote_entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeoZone {

    private String id;
    private Double latitude;
    private Double longitude;
    private String name;
    private String streetId;
}
