package org.mts.announcesservice.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeoZoneOutputDTO {

    private String name;
    private String id;
    private Double latitude;
    private Double longitude;
}
