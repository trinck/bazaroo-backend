package org.mts.announcesservice.dtos;


import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckUnitInputDTO {

    private Long id;
    @NotEmpty
    private String name;
    private String value;

}
