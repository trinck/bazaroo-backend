package org.mts.trackingservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * DTO for {@link org.mts.trackingservice.entities.DailyStats}
 */
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class DailyStatsOutputDTO implements Serializable {
    private  Long id;
    private  String adId;
    private  Long views;
    private  Long clicks;
    private  Long impressions;
    private  Long shares;
}