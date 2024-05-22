package org.mts.usersservice.dtos;


import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeOutputDTO extends  UserOutputDTO{

    private String CIN;
    private Double salary;
    private String postDescription;
}
