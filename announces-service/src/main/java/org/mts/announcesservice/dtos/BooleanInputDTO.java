package org.mts.announcesservice.dtos;


import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BooleanInputDTO extends FieldInputDTO{
    private Boolean value = true;
}
