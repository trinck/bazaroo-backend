package org.mts.locationservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Street {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private Long zip;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "street", orphanRemoval = true)
    private List<GeoZone> locations = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.PERSIST)
    private City city;
}
