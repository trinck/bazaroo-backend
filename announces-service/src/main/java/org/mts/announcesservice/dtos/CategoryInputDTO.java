package org.mts.announcesservice.dtos;


import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryInputDTO {

    private String id;
    @NotEmpty(message = "Title can not be empty")
    private String title;
    private String iconUrl;
    private String description;
    private String parentId;
    private String color;

}
