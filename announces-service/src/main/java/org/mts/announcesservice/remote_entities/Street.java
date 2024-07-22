package org.mts.announcesservice.remote_entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Street {
    private String id;
    private String name;
    private Long zip;
    private List<GeoZone> locations = new ArrayList<>();
    private String cityId;
}
