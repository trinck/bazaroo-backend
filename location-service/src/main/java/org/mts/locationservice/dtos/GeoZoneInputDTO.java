package org.mts.locationservice.dtos;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeoZoneInputDTO {

    private Long id;
    private Double latitude;
    private Double longitude;
    private String name;

}
