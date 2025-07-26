package org.mts.announcesservice.dtos;


import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnnounceTypeOutputDTO {


    private String id;
    private String name;
    private List<CategoryFieldOutputDTO> fields = new ArrayList<>();
}
