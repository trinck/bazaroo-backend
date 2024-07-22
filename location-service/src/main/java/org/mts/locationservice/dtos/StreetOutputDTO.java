package org.mts.locationservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StreetOutputDTO {
    private String id;
    private String name;
    private Long zip;
    private List<GeoZoneOutputDTO> locations = new ArrayList<>();
    private String cityId;
}
