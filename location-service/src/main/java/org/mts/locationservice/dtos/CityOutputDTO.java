package org.mts.locationservice.dtos;

import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mts.locationservice.entities.Country;
import org.mts.locationservice.entities.GenericsFieldsEntity;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class CityOutputDTO extends GenericsFieldsEntity {

    private String id;
    private String name;
    private String countryId;
}
