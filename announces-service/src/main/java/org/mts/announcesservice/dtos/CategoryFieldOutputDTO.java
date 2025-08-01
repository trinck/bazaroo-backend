package org.mts.announcesservice.dtos;



import com.fasterxml.jackson.annotation.JsonBackReference;
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
    private Boolean checked;
    private String icon;
    private List<CategoryFieldCheckUnit> fieldCheckUnits;
    private String announceTypeId;
    private String announceTypeName;
    @JsonBackReference
    private AnnounceTypeOutputDTO announceType;


}
