package org.mts.locationservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class CityInputDTO {

    private String id;
    private String name;
    private GeoZoneInputDTO location;
}
