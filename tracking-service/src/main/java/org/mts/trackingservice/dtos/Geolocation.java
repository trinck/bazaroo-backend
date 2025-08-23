package org.mts.trackingservice.dtos;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Geolocation {

    private Double lat;
    private Double lon;


}
