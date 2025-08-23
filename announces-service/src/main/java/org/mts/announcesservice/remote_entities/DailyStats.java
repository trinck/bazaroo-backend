package org.mts.announcesservice.remote_entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;


@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DailyStats {


    private Long id;
    private Date timestamp;
    private String adId;//announce id

    private Long views = 0L;
    private Long clicks = 0L;
    private Long impressions = 0L;
    private Long shares = 0L;

    public void incrementViews() {
        ++this.views;
    }

    public void incrementClicks() {
        ++this.clicks;
    }

    public void incrementImpressions() {
        ++this.impressions;
    }

    public void incrementShares() {
        ++this.shares;
    }
}
