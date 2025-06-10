package org.mts.announcesservice.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryOutputDTO2 {
    private String id;
    private String title;
    private String iconUrl;
    private String description;
    private String color;
    private ParentDto parentCategory;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ParentDto {
        private String id;
        private String title;
    }
}
