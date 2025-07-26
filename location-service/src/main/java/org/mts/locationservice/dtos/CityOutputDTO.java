package org.mts.locationservice.dtos;



import lombok.*;
import org.mts.locationservice.entities.GenericsFieldsEntity;

import java.util.List;

@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
public class CityOutputDTO extends GenericDto {

    private String id;
    private String name;
    private String countryId;
    private String countryName;
    private List<StreetOutputDTO> streets;

}
