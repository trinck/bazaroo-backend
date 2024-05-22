package org.mts.usersservice.dtos;


import lombok.*;


@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeInputDTO extends UserInputDTO{


    private String CIN;
    private Double salary;
    private String postDescription;


}
