package org.mts.announcesservice.dtos;



import lombok.*;
import org.mts.announcesservice.entities.CategoryFieldCheckUnit;
import org.mts.announcesservice.enums.FieldType;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryFieldOutputDTO {

    private Long id;
    private String fieldName;
    private FieldType type;
    private List<CategoryFieldCheckUnit> fieldCheckUnits;


}
