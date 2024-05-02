package org.mts.imagesservice.entities;


import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
}
