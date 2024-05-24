package org.mts.announcesservice.dtos;


import lombok.*;

import java.util.ArrayList;
import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckInputDTO extends FieldInputDTO{

    protected List<CheckUnitInputDTO> checkUnits = new ArrayList<>();
}
