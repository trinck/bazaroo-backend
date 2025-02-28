package org.mts.trackingservice.services;

import org.mts.trackingservice.documents.TrackingEventDocument;
import org.mts.trackingservice.entities.DailyStats;
import org.mts.trackingservice.repositories.DailyStatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class StatsService implements IStatsService{


    @Autowired
    private DailyStatsRepository statsRepository;

    /**
     * @param event
     */
    @Override
    public void updateDailyStats(TrackingEventDocument event) {

        DailyStats stats = statsRepository.findByAdId(event.getAdId())
                .orElse(DailyStats.builder().adId(event.getAdId()).timestamp(new Date()).build());
        this.incrementTargetMetric(stats,event);

        this.statsRepository.save(stats);

    }

    /**
     * @param adId
     * @return {@link org.mts.trackingservice.entities.DailyStats}
     * @throws java.util.NoSuchElementException
     */
    @Override
    public DailyStats getDailyStatsByAdId(String adId) {

        return this.statsRepository.findByAdId(adId).orElse(new DailyStats());
    }

    /**
     * @param lotAdId list of adId
     * @return
     */
    @Override
    public List<DailyStats> getDailyStatsForLot(List<String> lotAdId) {
        return this.statsRepository.findAllByAdIdIn(lotAdId);
    }

    /**
     * @param adId
     * @param events
     */
    @Override
    public void updateAllDailyStats(String adId, List<TrackingEventDocument> events) {

        DailyStats stats = statsRepository.findByAdId(adId)
                .orElse(DailyStats.builder().adId(adId).timestamp(new Date()).views(0L).impressions(0L).clicks(0L).shares(0L).build());

        for (TrackingEventDocument event: events){
           this.incrementTargetMetric(stats,event);
        }

        this.statsRepository.save(stats);

    }

    /**
     * increment view or share ... according to event tracking metric
     * @param stats
     * @param event
     * */
    private void incrementTargetMetric(DailyStats stats, TrackingEventDocument event){
        switch (event.getEventType()) {
            case VIEW:
                stats.incrementViews();
                break;
            case CLICK:
                stats.incrementClicks();
                break;
            case IMPRESSION:
                stats.incrementImpressions();
                break;
            case SHARE: stats.incrementShares();
                break;
            default: break;
        }
    }
}
