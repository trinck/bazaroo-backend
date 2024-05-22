package org.mts.announcesservice.dtos;


import jakarta.validation.constraints.Size;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShortTextInputDTO extends FieldInputDTO{

    @Size(max = 255, message = "Short text size must be smaller than 255")
    private String value = "";
}
