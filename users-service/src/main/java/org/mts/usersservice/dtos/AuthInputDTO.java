package org.mts.usersservice.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthInputDTO {

    private String id;
    private String username;
    private String password;
    private Long roleId;
}
