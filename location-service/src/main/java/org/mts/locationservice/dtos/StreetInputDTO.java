package org.mts.locationservice.dtos;



import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StreetInputDTO extends GenericDto{

    private String id;
    private String name;
    private Long zip;

}
