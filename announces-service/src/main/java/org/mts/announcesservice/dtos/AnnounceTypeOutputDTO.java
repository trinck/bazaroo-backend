package org.mts.announcesservice.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnnounceTypeOutputDTO {


    private String id;
    private String name;
    private List<CategoryFieldOutputDTO> fields = new ArrayList<>();
}
