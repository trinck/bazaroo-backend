package org.mts.announcesservice.dtos;


import lombok.*;
import org.mts.announcesservice.entities.GeoZone;

import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnnounceInputDTO {

    private String id;
    private Double price;
    private String streetId;
    private GeoZone location;
    private String tel;
    private String title;
    private String address;
    private String description;
    private List<Object> ObjectFields = new ArrayList<>();

}
