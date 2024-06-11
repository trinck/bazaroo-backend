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
public class CategoryFieldCheckUnitInputDTO {

    private String id;
    @NotEmpty
    private String name;
    private String dataValue = name;
}
