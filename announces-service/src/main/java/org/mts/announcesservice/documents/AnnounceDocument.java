package org.mts.announcesservice.documents;

import jakarta.persistence.*;
import lombok.*;
import org.mts.announcesservice.dtos.FieldOutputDTO;
import org.mts.announcesservice.dtos.GeoZoneInputDTO;
import org.mts.announcesservice.dtos.GeoZoneOutputDTO;
import org.mts.announcesservice.entities.GeoZone;
import org.mts.announcesservice.remote_entities.Media;
import org.mts.announcesservice.remote_entities.Street;
import org.springframework.data.elasticsearch.annotations.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(indexName = "announces")
public class AnnounceDocument {
    @Id
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private String id;
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private String tenantId;

    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private String typeName;
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Double)
    private Double price;
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private String streetId;
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private String cityId;
    @GeoPointField
    private GeoZoneInputDTO location;
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Text)
    private String title;
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Text)
    private String tel;
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Date, format = DateFormat.date_time)
    private Date postedAt;
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Text)
    private String address;
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Text)
    private String description;
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private String userId;
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Text)
    private String status;
    @Transient
    private Street street;

    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private String categoryTitle;
    @ToString.Exclude
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Nested)
    private List<FieldOutputDTO> fields = new ArrayList<>();
    @Transient
    private List<Media> medias = new ArrayList<>();
}
