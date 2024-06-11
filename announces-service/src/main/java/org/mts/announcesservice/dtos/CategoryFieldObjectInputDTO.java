package org.mts.announcesservice.dtos;


import lombok.*;
import org.mts.announcesservice.entities.CategoryFieldCheckUnit;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CategoryFieldObjectInputDTO extends CategoryFieldInputDTO {
    private List<CategoryFieldCheckUnit> fieldCheckUnits = new ArrayList<>();
}
