package org.mts.announcesservice.dtos;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mts.announcesservice.enums.FieldType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryFieldOutputDTO {

    private Long id;
    private String fieldName;
    private FieldType type;

}
