package org.mts.locationservice.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.geo.Point;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GeoPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double lat;
    private Double lon;
}
