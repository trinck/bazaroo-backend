package org.mts.imagesservice.entities;


import jakarta.persistence.MappedSuperclass;
import lombok.*;

import java.util.Date;

@MappedSuperclass
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public abstract class GenericsFieldsEntity {


    private Date createdAt;
    private Date lastModifiedAt;
    private String history;
}
