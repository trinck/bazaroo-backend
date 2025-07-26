package org.mts.locationservice.dtos;

import lombok.*;

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
    private String cityId;
    private String cityName;
}
