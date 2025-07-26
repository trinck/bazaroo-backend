package org.mts.announcesservice.dtos;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeoZoneInputDTO {
    private Double lat;
    private Double lon;
}
