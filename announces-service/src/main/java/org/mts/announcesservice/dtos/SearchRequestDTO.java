package org.mts.announcesservice.dtos;

import lombok.*;
import org.mts.announcesservice.enums.SearchTypes;


import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@ToString
public class SearchRequestDTO {
    private Map<String, Object> filters;
    private String query;
    private String order = "asc";
    private int page;
    private int size = 10;
    private String sortBy;
    private SearchTypes searchBy = SearchTypes.NEAR_BY;
    private Map<String, Object> locationsMode;
    private String tenantId;



}
