package org.mts.locationservice.dtos;

import jakarta.persistence.Column;
import lombok.*;
import org.mts.locationservice.entities.GenericsFieldsEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GeoZoneOutputDTO extends GenericsFieldsEntity {

    private Long id;
    private Double latitude;
    private Double longitude;
    private String name;
}
