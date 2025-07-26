package org.mts.locationservice.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MappedSuperclass;

import jakarta.persistence.OneToOne;
import lombok.*;

import java.util.Date;

@MappedSuperclass
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public abstract class GenericsFieldsEntity {

    protected Date createdAt;
    protected Date lastModifiedAt;
    protected String history;
    protected Boolean verified = false;
    protected String placeId;
    protected Boolean active = true;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "location_id")
    protected GeoPoint location;
    @OneToOne
    @JoinColumn(name = "northeast_id")
    protected GeoPoint northeast;
    @OneToOne
    @JoinColumn(name = "southwest_id")
    protected GeoPoint southwest;

}
