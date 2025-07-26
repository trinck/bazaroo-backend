package org.mts.announcesservice.dtos;


import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeoZoneOutputDTO {

    private String id;
    private Double lat;
    private Double lon;
}
