package org.mts.announcesservice.documents;

import jakarta.persistence.*;
import lombok.*;
import org.mts.announcesservice.entities.AnnounceType;
import org.mts.announcesservice.entities.Category;
import org.mts.announcesservice.entities.Field;
import org.mts.announcesservice.entities.GeoZone;
import org.mts.announcesservice.enums.AnnounceStatus;
import org.springframework.data.elasticsearch.annotations.DateFormat;
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
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Object)
    private GeoZone location;
    private String title;
    private String tel;
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Date, format = DateFormat.basic_date_time)
    private Date postedAt;
    private String address;
    private String description;
    private String userId;
    private String status ;

    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Object)
    private Category category;
    @ToString.Exclude
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Nested, dynamic = Dynamic.TRUE)
    private List<Field> fields = new ArrayList<>();
}
