package org.mts.trackingservice.services;

import org.mts.trackingservice.documents.TrackingEventDocument;

import java.util.List;

public interface IElasticsearchService {

    public TrackingEventDocument save(TrackingEventDocument event);
    public List<TrackingEventDocument> saveAll(List<TrackingEventDocument> list);
}
