package org.mts.announcesservice.dtos;


import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnnounceInputDTO {

    private String id;
    private Double price;
    private String cityId;
    private Long locationId;
    private String title;
    private String address;
    private String description;
    private List<Object> ObjectFields = new ArrayList<>();

}
