package org.mts.trackingservice.documents;



import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mts.trackingservice.dtos.Geolocation;
import org.mts.trackingservice.enums.EventType;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Document(indexName = "tracking_event")
@Data
@NoArgsConstructor
public class TrackingEventDocument implements Serializable {

    @Id
    @Field(type = FieldType.Keyword)
    private String id;

    @Field(type = FieldType.Keyword)
    private String adId;//announce id
    @Field(type = FieldType.Keyword)
    private String ipAddress; //User's Address IP
    @Field(type = FieldType.Keyword)
    private String userAgent;
    @Field(type = FieldType.Keyword)
    private EventType eventType;
    @Field(type = FieldType.Date, format = DateFormat.date_time)
    private Date timestamp;
    @Field(type = FieldType.Keyword)
    private String userId;
    @Field(type = FieldType.Object)
    private Geolocation geoLocation;

}
