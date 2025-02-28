package org.mts.trackingservice.services;


import lombok.extern.slf4j.Slf4j;
import org.mts.trackingservice.documents.TrackingEventDocument;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Slf4j
@Service
public class BatchService implements IBatchService {


    private IStatsService iStatsService;
    private IElasticsearchService elasticsearchService;
    private StreamBridge streamBridge;

    public BatchService(IStatsService iStatsService, IElasticsearchService elasticsearchService, StreamBridge streamBridge) {
        this.iStatsService = iStatsService;
        this.elasticsearchService = elasticsearchService;
        this.streamBridge = streamBridge;
    }

    /**
     *
     */
    @Override
    public void persistTrackingEventsFromRedis() {
       /* Map<String, List<TrackingEventDocument>> listMap = this.redisService.getAllTrackingEvents();
        for (String key: listMap.keySet() ){
            this.iStatsService.updateAllDailyStats(key, listMap.get(key));
            this.elasticsearchService.saveAll(listMap.get(key));
        }*/
    }

    @Scheduled(fixedRate = 30000) // 🔄
    public void sendHeartbeat() {
        TrackingEventDocument heartbeatEvent = new TrackingEventDocument();
        heartbeatEvent.setAdId("HEARTBEAT");
        heartbeatEvent.setTimestamp(new Date());

        streamBridge.send("tracking-events", heartbeatEvent);

    }
}
