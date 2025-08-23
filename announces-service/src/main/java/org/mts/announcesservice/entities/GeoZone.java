package org.mts.announcesservice.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@ToString
public class GeoZone {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private Double lat;
    private Double lon;
    @OneToOne(mappedBy = "location")
    @ToString.Exclude
    private Announce announce;

}
