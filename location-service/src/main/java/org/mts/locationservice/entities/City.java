package org.mts.locationservice.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class City extends GenericsFieldsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(nullable = false)
    private String name;
    @ManyToOne
    private Country country;

    @OneToMany(mappedBy = "city", cascade = CascadeType.PERSIST)
    private List<Street> streets = new ArrayList<>();

}
