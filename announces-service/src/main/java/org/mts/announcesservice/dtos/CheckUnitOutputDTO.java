package org.mts.announcesservice.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CheckUnitOutputDTO {

    private Long id;
    @NotEmpty
    private String name;
    private String dataValue;
    private Boolean checked;
}
