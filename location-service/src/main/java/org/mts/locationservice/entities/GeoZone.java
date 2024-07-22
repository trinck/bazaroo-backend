package org.mts.locationservice.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Entity
public class GeoZone extends GenericsFieldsEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private Double latitude;
    private Double longitude;
    private String name;
    @ManyToOne()
    private Street street;

}
