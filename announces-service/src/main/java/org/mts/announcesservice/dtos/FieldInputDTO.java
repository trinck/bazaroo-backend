package org.mts.announcesservice.dtos;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mts.announcesservice.enums.FieldType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FieldInputDTO {
    protected String id;
    @NotEmpty
    protected String name;
    @NotNull
    protected FieldType fieldType;

}
