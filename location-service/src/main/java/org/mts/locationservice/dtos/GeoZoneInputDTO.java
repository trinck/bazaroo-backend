package org.mts.locationservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeoZoneInputDTO {

    private String id;
    private Double latitude;
    private Double longitude;
    private String name;

}
