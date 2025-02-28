package org.mts.trackingservice.consumers;


import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.*;
import org.mts.trackingservice.documents.TrackingEventDocument;
import org.mts.trackingservice.serdes.JsonSerde;
import org.mts.trackingservice.serdes.ListTrackingEventDocumentSerde;
import org.mts.trackingservice.services.IElasticsearchService;
import org.mts.trackingservice.services.IRedisService;
import org.mts.trackingservice.services.IStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

@Component
@Slf4j
public class EventsConsumer {


    @Autowired
    private IRedisService redisService;
    @Autowired
    private IStatsService statsService;
    @Autowired
    private IElasticsearchService elasticsearchService;


    //@Bean
    public Consumer<TrackingEventDocument> trackingEventConsumer(){

        return (input)->{
            //this.redisService.enqueueTrackingEvent(input);
            log.info(input.toString());
        };
    }


    @Bean
    public Function<KStream<String, TrackingEventDocument>, KStream<String, List<TrackingEventDocument>>> ksProcessTrackingEvents() {
        return input -> {

            KTable<Windowed<String>, List<TrackingEventDocument>> aggregatedEvents = input
                    //.filterNot((s, eventDocument) ->eventDocument.getAdId().equals("HEARTBEAT") )
                    .map((s, eventDocument) -> new KeyValue<>(eventDocument.getAdId(),eventDocument))
                    .groupByKey(Grouped.with(Serdes.String(), new JsonSerde<>(TrackingEventDocument.class)))
                    .windowedBy(TimeWindows.ofSizeWithNoGrace(Duration.ofSeconds(30)))
                    //.windowedBy(SlidingWindows.ofTimeDifferenceWithNoGrace(Duration.ofSeconds(30))) // ⏳ Tmp window

                    .aggregate(ArrayList::new, (key, event, list)->{
                        list.add(event);
                        return list;
                    }, Materialized.with(Serdes.String(), new ListTrackingEventDocumentSerde()))
                    //.suppress(Suppressed.untilTimeLimit(Duration.ofSeconds(5), Suppressed.BufferConfig.unbounded())); // ✅ Forcer la publication après 5s
                    .suppress(Suppressed.untilWindowCloses(Suppressed.BufferConfig.unbounded()));

            // 🔥 Save to database after window closed
              this.saveToDatabase(aggregatedEvents);

            return aggregatedEvents.toStream()
                    .map((windowedKey, events) -> KeyValue.pair(windowedKey.key(), events));
        };
    }


    private void saveToDatabase(KTable<Windowed<String>, List<TrackingEventDocument>> events) {

        events.toStream().filterNot((stringWindowed, list) -> stringWindowed.key().equals("HEARTBEAT")).foreach((stringWindowed, list) -> {
           System.out.println("**********"+stringWindowed.key()+"***************");
           list.forEach(eventDocument -> {
               System.out.println(eventDocument.getTimestamp());
           });

                this.statsService.updateAllDailyStats(stringWindowed.key(), list);
                this.elasticsearchService.saveAll(list);

        });

    }
}
