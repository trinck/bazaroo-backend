package org.mts.locationservice.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class Country extends GenericsFieldsEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(unique = true)
    private String name;
    @Column(unique = true)
    private String code;
    @OneToMany(mappedBy = "country", cascade = CascadeType.PERSIST)
    private List<City> cities = new ArrayList<>();
}
