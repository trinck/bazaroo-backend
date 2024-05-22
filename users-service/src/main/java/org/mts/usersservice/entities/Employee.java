package org.mts.usersservice.entities;

import jakarta.persistence.Entity;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employee extends User{

    private String CIN;
    private Double salary;
    private String postDescription;

}
