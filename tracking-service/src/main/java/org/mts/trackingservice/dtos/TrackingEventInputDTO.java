package org.mts.trackingservice.dtos;


import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class TrackingEventInputDTO {
    private String adId;//announce id
    private Geolocation geoLocation;
}
