package org.mts.announcesservice.dtos;


import lombok.*;

import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckInputDTO extends FieldInputDTO{

    protected Set<CheckUnitInputDTO> checkUnits = new HashSet<>();
}
