package org.mts.trackingservice.services;

import org.modelmapper.ModelMapper;
import org.mts.trackingservice.documents.TrackingEventDocument;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ElasticsearchService implements IElasticsearchService {

    private final ElasticsearchOperations operations;
    private final ModelMapper mapper;

    public ElasticsearchService(ElasticsearchOperations operations, ModelMapper mapper) {
        this.operations = operations;
        this.mapper = mapper;
    }

    /**
     * @param event
     * @return
     */
    @Override
    public TrackingEventDocument save(TrackingEventDocument event) {

        return this.operations.save(event);
    }

    /**
     * @param list
     * @return
     */
    @Override
    public List<TrackingEventDocument> saveAll(List<TrackingEventDocument> list) {
        return (List<TrackingEventDocument>) this.operations.save(list);

    }
}
