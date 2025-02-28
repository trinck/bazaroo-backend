package org.mts.trackingservice.services;

public interface IBatchService {
    public void persistTrackingEventsFromRedis();
}
