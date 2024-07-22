package org.mts.locationservice.dtos;


import lombok.*;
import org.mts.locationservice.entities.GenericsFieldsEntity;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeoZoneOutputDTO extends GenericsFieldsEntity {

    private String id;
    private Double latitude;
    private Double longitude;
    private String name;
    private String streetId;
}
