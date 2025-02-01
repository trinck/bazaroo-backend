package org.mts.announcesservice.dtos;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mts.announcesservice.entities.AnnounceType;
import org.mts.announcesservice.entities.Category;
import org.mts.announcesservice.entities.Field;
import org.mts.announcesservice.entities.GeoZone;
import org.mts.announcesservice.enums.AnnounceStatus;
import org.mts.announcesservice.remote_entities.Media;
import org.mts.announcesservice.remote_entities.Street;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnnounceOutputDTO {

    private String id;
    private AnnounceTypeOutputDTO type;
    private Double price;
    private Street street;
    private GeoZoneOutputDTO location;
    private String title;
    private Date postedAt;
    private String address;
    private String description;
    private String tel;
    private String userId;
    private AnnounceStatus status;
    private List<Media> medias = new ArrayList<>();

    private CategoryOutputDTO category;
    private List<FieldOutputDTO> fields = new ArrayList<>();
}
