package org.mts.announcesservice.dtos;


import jakarta.validation.constraints.Size;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TextInputDTO extends FieldInputDTO {

    @Size(max = 500, message = "Max size 500")
    private String value = "";
}
