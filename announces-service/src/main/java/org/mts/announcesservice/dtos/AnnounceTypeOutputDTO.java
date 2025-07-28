package org.mts.announcesservice.dtos;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.mts.announcesservice.entities.Category;

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
    @JsonBackReference
    private CategoryOutputDTO category;
}
