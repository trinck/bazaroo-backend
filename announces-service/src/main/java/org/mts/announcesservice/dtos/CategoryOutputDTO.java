package org.mts.announcesservice.dtos;



import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.mts.announcesservice.entities.Category;


import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryOutputDTO {

    private String id;
    private String title;
    private String iconUrl;
    private String color;
    private String description;
    private Set<CategoryOutputDTO> subCategories = new HashSet<>();
    private String parentCategoryId;
    private String parentCategoryTitle;
    @JsonBackReference
    private CategoryOutputDTO parentCategory;
    private Set<AnnounceTypeOutputDTO> types = new HashSet<>();
}
