package org.mts.usersservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mts.usersservice.enums.EnumRole;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleOutputDTO {

    private Long id;
    private EnumRole role;
    private String description;

}
