package org.mts.announcesservice.documents;

import jakarta.persistence.*;
import lombok.*;
import org.mts.announcesservice.entities.AnnounceType;
import org.mts.announcesservice.entities.Category;
import org.mts.announcesservice.entities.Field;
import org.mts.announcesservice.entities.GeoZone;
import org.mts.announcesservice.enums.AnnounceStatus;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Dynamic;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "announces")
public class AnnounceDocument {
    @Id
    private String id;

    private AnnounceType type;
    private Double price;
    private String streetId;
    private GeoZone location;
    private String title;
    private String tel;
    private Date postedAt;
    private String address;
    private String description;
    private String userId;
    private String status ;


    private Category category;
    @ToString.Exclude
    private List<Field> fields = new ArrayList<>();
}
