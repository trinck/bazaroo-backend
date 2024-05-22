package org.mts.announcesservice.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnnounceTypeOutputDTO {


    private String id;
    private String name;
    private Set<CategoryFieldOutputDTO> fields = new HashSet<>();
}
