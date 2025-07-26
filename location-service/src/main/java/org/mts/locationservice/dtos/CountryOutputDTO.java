package org.mts.locationservice.dtos;

import lombok.*;
import org.mts.locationservice.entities.GenericsFieldsEntity;

import java.util.List;


@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class CountryOutputDTO extends GenericsFieldsEntity {


    private String id;
    private String name;
    private String code;
    private String currency;
    private List<CityOutputDTO> cities;
}
