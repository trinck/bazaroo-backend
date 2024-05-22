package org.mts.announcesservice.dtos;


import lombok.*;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnnounceInputDTO {

    private String id;
    private Double price;
    private String CityId;
    private Long locationId;
    private String title;
    private String address;
    private String description;

}
