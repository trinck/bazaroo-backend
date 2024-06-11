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



    protected Date createdAt;
    protected Date lastModifiedAt;
    protected String history;
    protected Boolean verified = false;
    protected Boolean active = true;

}
