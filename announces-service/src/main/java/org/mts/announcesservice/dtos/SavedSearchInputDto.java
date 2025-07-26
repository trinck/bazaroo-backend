package org.mts.announcesservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mts.announcesservice.enums.SavedSearchScheduleType;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Map;

/**
 * DTO for {@link org.mts.announcesservice.entities.SavedSearch}
 */
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class SavedSearchInputDto implements Serializable {
    private  Long id;
    private  SavedSearchScheduleType type;
    private  LocalTime time;
    private  String searchName;
    private  Map<String, Object>  criteria;
    private  Boolean active;
}