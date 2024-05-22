package org.mts.usersservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mts.usersservice.entities.Role;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthOutputDTO {


    private String id;
    private String email;
    private String username;
    private String password;
    private RoleOutputDTO role;
    private ConnectionOutputDTO connection;
}
