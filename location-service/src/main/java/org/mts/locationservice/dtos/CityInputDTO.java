package org.mts.locationservice.dtos;

import lombok.*;
import org.mts.locationservice.entities.GenericsFieldsEntity;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CityInputDTO extends GenericDto {

    private String id;
    private String name;

}
