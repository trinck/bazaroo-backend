package org.mts.locationservice.dtos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.mts.locationservice.entities.City;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class StreetOutputDTO extends GenericDto{
    private String id;
    private String name;
    private Long zip;
    private String cityName;
    private String cityId;
    @JsonBackReference
    private CityOutputDTO city;
}
