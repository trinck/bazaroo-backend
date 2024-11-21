package org.mts.announcesservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class GeoZone {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Double)
    private Double latitude;
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Double)
    private Double longitude;
    @OneToOne
    private Announce announce;

}
