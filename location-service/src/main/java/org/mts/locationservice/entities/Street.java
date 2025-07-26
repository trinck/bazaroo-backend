package org.mts.locationservice.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class Street extends GenericsFieldsEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private Long zip;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private City city;
}
