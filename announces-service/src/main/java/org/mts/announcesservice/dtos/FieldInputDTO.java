package org.mts.announcesservice.dtos;


import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class FieldInputDTO {
    protected Long id;
    @NotEmpty
    protected String name;

}
