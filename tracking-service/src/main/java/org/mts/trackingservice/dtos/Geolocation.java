package org.mts.trackingservice.dtos;
import jakarta.persistence.Embeddable;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Geolocation {

    private Double latitude;
    private Double longitude;
    private String city;
    private String country;

}
