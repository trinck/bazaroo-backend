package org.mts.announcesservice.documents;

import jakarta.persistence.Id;
import lombok.*;
import org.mts.announcesservice.dtos.FieldOutputDTO;
import org.mts.announcesservice.dtos.GeoZoneInputDTO;
import org.mts.announcesservice.dtos.GeoZoneOutputDTO;
import org.mts.announcesservice.remote_entities.Street;
import org.springframework.data.elasticsearch.annotations.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Document(indexName = "saved_searches")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class SavedSearchDocument {

    @Id
    @Field(type = FieldType.Keyword)
    private String id;

    @Field(type = FieldType.Text)
    private String title;
    @Field(type = FieldType.Keyword)
    private String tenantId;

    @Field(type = FieldType.Percolator)
    private Map<String, Object> query; // On mettra null ici mais ce champ est obligatoire pour activer percolator

    @Field(type = FieldType.Keyword)
    private String type;

    @Field(type = FieldType.Boolean)
    private Boolean active;

    @Field(type = FieldType.Keyword)
    private String userId;

    @Field(type = FieldType.Keyword)
    private String typeName;
    @Field(type = FieldType.Double)
    private Double price;
    @GeoPointField
    private GeoZoneInputDTO location;
    @Field(type = FieldType.Text)
    private String tel;
    @Field(type = FieldType.Date, format = DateFormat.date_time)
    private Date postedAt;
    @Field(type = FieldType.Text)
    private String address;
    @Field(type = FieldType.Text)
    private String description;
    @Field(type = FieldType.Text)
    private String status;
    @Field(type = FieldType.Keyword)
    private String streetId;
    @Field(type = FieldType.Keyword)
    private String cityId;
    @Field(type = FieldType.Long)
    private Long views;
    @Field(type = FieldType.Long)
    private Long clicks;
    @Field(type = FieldType.Long)
    private Long impressions;
    @Field(type = FieldType.Long)
    private Long imagesLength;

    @Field(type = FieldType.Keyword)
    private String categoryTitle;
    @Field(type = FieldType.Keyword)
    private String categoryParentCategoryTitle;
    @ToString.Exclude
    @Field(type = FieldType.Nested)
    private List<FieldOutputDTO> fields = new ArrayList<>();

}
