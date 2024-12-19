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
    private Double latitude;
    private Double longitude;
    @OneToOne
    private Announce announce;

}
