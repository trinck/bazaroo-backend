package org.mts.locationservice.entities;

import jakarta.persistence.MappedSuperclass;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class GenericsFieldsEntity {



    private Date createdAt;
    private Date lastModifiedAt;
    private String history;
    private Boolean verified = false;
    private Boolean active = true;

}
