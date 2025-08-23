package org.mts.usersservice.remote_entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Street {
    private String id;

    private String name;

    private Long zip;

    private String cityId;
    private String cityName;
}
