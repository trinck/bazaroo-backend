package org.mts.announcesservice.dtos;


import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mts.announcesservice.enums.FieldType;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class FieldOutputDTO {
    protected String id;
    @NotEmpty
    protected String name;
    protected Object dataValue;
    private  FieldType type;

}
