package org.mts.announcesservice.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.mts.announcesservice.enums.SavedSearchScheduleType;

import java.time.LocalTime;
import java.util.Map;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SavedSearchRequest {

    private Long id;
    private String searchName;
    private SearchRequestDTO criteria;
    private LocalTime time;
    private SavedSearchScheduleType type;
    private  Boolean active;
    private String tenantId;

}
