package org.mts.locationservice.dtos;

import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mts.locationservice.entities.GeoPoint;

import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GenericDto {

    protected Date createdAt;
    protected Date lastModifiedAt;
    protected String history;
    protected String placeId;
    protected GeoPoint location;
    protected GeoPoint northeast;
    protected GeoPoint southwest;
}
