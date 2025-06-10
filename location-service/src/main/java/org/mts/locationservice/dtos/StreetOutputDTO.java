package org.mts.locationservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mts.locationservice.entities.City;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StreetOutputDTO {
    private String id;
    private String name;
    private Long zip;
    private City city;
}
