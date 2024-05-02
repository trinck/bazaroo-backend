package org.mts.locationservice.dtos;

import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;
import org.mts.locationservice.entities.City;
import org.mts.locationservice.entities.GenericsFieldsEntity;

import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class CountryOutputDTO extends GenericsFieldsEntity {


    private String id;
    private String name;
    private String code;

    private List<CountryOutputDTO> cities;
}
