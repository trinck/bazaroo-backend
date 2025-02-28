package org.mts.trackingservice.services;

import org.mts.trackingservice.documents.TrackingEventDocument;

import java.util.List;
import java.util.Map;

public interface IRedisService {

    public List<TrackingEventDocument> getTrackingEvents(String adId);
    public Map<String,List<TrackingEventDocument>> getAllTrackingEvents();
    public void enqueueTrackingEvent(TrackingEventDocument event) ;

}
