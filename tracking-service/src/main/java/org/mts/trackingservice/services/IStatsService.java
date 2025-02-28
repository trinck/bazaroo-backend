package org.mts.trackingservice.services;

import org.mts.trackingservice.documents.TrackingEventDocument;
import org.mts.trackingservice.entities.DailyStats;

import java.util.List;

public interface IStatsService {

    /**
     * Update only one unit of ad stats ( ex ++view, view = view + 1)
     * @param event
     * */
    public void updateDailyStats(TrackingEventDocument event);

    /**
     * Return DailyStats for ad by adId
     * @param adId
     * */
    public DailyStats getDailyStatsByAdId(String adId);

    /**
     * retrieve  stats for all adverts so the id is in lotAdId
     * @param lotAdId list of adId
     * @return  {@code List<DailyStats> } list of DailyStats
     * */
    public List<DailyStats> getDailyStatsForLot(List<String> lotAdId);

    /**
     * Update ad stats with many of metric units ( views, shares...)
     * @param adId
     * @param events
     * */
    public void updateAllDailyStats(String adId, List<TrackingEventDocument> events);
}
