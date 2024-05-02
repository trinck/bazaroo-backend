package org.mts.locationservice.dtos;

import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mts.locationservice.entities.Country;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class CityInputDTO {

    private String id;
    private String name;

    private String countryId;
}
