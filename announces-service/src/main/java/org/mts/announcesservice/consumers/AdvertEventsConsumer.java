package org.mts.announcesservice.consumers;


import co.elastic.clients.elasticsearch._types.SortOrder;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.mts.announcesservice.documents.AnnounceDocument;
import org.mts.announcesservice.entities.Announce;
import org.mts.announcesservice.entities.Preference;
import org.mts.announcesservice.entities.SavedSearch;
import org.mts.announcesservice.enums.*;
import org.mts.announcesservice.remote_entities.DailyStats;
import org.mts.announcesservice.repositories.SavedSearchRepository;
import org.mts.announcesservice.service.IAnnounceSearchService;
import org.mts.announcesservice.service.IAnnounceService;
import org.mts.announcesservice.service.IPercolateSearchService;
import org.mts.announcesservice.service.IPreferenceService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@Component
@Slf4j
public class AdvertEventsConsumer {


    private final IAnnounceSearchService searchService;
    private final StreamBridge bridge;
    private final SavedSearchRepository savedSearchRepository;
    private final IPreferenceService preferenceService;
    private final IPercolateSearchService percolateSearchService;
    private final IAnnounceService announceService;
    private final ModelMapper modelMapper;
    private final ElasticsearchOperations operations;
    @Value("${app.broker.topics.notification}")
    private  String NOTIFICATION_TOPIC;
    @Value("${spring.application.name}")
    private String APPLICATION_NAME;

    public AdvertEventsConsumer(IAnnounceSearchService searchService, StreamBridge bridge, SavedSearchRepository savedSearchRepository, IPreferenceService preferenceService, IPercolateSearchService percolateSearchService, IAnnounceService announceService, ModelMapper modelMapper, ElasticsearchOperations operations) {
        this.searchService = searchService;
        this.bridge = bridge;
        this.savedSearchRepository = savedSearchRepository;
        this.preferenceService = preferenceService;
        this.percolateSearchService = percolateSearchService;
        this.announceService = announceService;
        this.modelMapper = modelMapper;
        this.operations = operations;
    }


    @Bean
    public Consumer<Map<String, Object>> advertEvents() {
        return (map -> {
            String adId = (String) map.get("adId");
            AdvertEventType eventType = AdvertEventType.valueOf((String)map.get("type"));
            switch (eventType) {
                case NEW -> this.notifySearchers(adId);
                case DELETED, EXPIRED, UPDATED, ARCHIVED -> {
                }
            }

        });
    }


    @Bean
    public Consumer<DailyStats> adsDailyStats() {
        return (stats) -> {
            log.info("Updating adsDailyStats {}", stats);
            Announce announce = this.announceService.getByID(stats.getAdId());
            announce.setViews(stats.getViews());
            announce.setClicks(stats.getClicks());
            announce.setImpressions(stats.getImpressions());
            try {
                this.announceService.update(announce);
            } catch (Exception e) {
                log.error("Error on update announce stats for id {} , with message : {}",announce.getId(), e.getMessage());
                // TODO send error to topic DLQ
                throw new RuntimeException(e);

            }

        };
    }



    private void notifySearchers(String adId) {

        AnnounceDocument announceDocument = this.operations.get(adId, AnnounceDocument.class);
        if (announceDocument == null){
            return;
        }
        announceDocument.setPostedAt(null);
        log.info(announceDocument.toString());
        List<String> userIds = this.percolateSearchService.findMatchingUsers(announceDocument);

        if (userIds.isEmpty()) {
            return;
        }

        // Notify users
        for (String user : userIds) {
            Map<String, Object> data = new HashMap<>();
            data.put("adId", adId);
            data.put("postedBy",announceDocument.getUserId());
           // data.put("image",announceDocument.getMedias().get(0));
            this.bridge.send(this.NOTIFICATION_TOPIC, Map.of("userId", user, "message", "Une annonce qui pourrait vous intéresser vient d'être publiée", "type", NotificationType.ANNOUNCE_MATCH.name(), "targetType", NotificationTargetType.PRIVATE, "audience", NotificationAudience.ALL, "service",this.APPLICATION_NAME, "data", data, "title","Match"));
        }


    }


}
