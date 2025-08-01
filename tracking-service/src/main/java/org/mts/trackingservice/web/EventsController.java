package org.mts.trackingservice.web;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.mts.trackingservice.documents.TrackingEventDocument;
import org.mts.trackingservice.dtos.DailyStatsOutputDTO;
import org.mts.trackingservice.dtos.TrackingEventInputDTO;
import org.mts.trackingservice.enums.EventType;
import org.mts.trackingservice.services.IStatsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("tracking-events")
public class EventsController {


    private final StreamBridge bridge;
    private final ModelMapper mapper;
    private final IStatsService statsService;
    @Value("${app.broker.topics.tracking-in}")
    private String TOPIC_TRACKING_EVENTS_IN;
    public EventsController(StreamBridge bridge, ModelMapper mapper,  IStatsService statsService) {
        this.bridge = bridge;
        this.mapper = mapper;
        this.statsService = statsService;
    }


    @PostMapping("/view")
    public boolean addView(@RequestBody TrackingEventInputDTO dto, HttpServletRequest request) {
        return this.bridge.send(this.TOPIC_TRACKING_EVENTS_IN, initTrackingEvent(dto, EventType.VIEW, request));

    }

    @PostMapping("/impression")
    public boolean addImpression(@RequestBody TrackingEventInputDTO dto, HttpServletRequest request) {
        return this.bridge.send(this.TOPIC_TRACKING_EVENTS_IN, initTrackingEvent(dto, EventType.IMPRESSION, request));

    }

    @PostMapping("/share")
    public boolean addShare(@RequestBody TrackingEventInputDTO dto, HttpServletRequest request) {
        return this.bridge.send(this.TOPIC_TRACKING_EVENTS_IN, initTrackingEvent(dto, EventType.SHARE, request));

    }



    @PostMapping("/click")
    public boolean addClick(@RequestBody TrackingEventInputDTO dto, HttpServletRequest request) {

        return this.bridge.send(this.TOPIC_TRACKING_EVENTS_IN, initTrackingEvent(dto, EventType.CLICK, request));

    }

    private TrackingEventDocument initTrackingEvent(@RequestBody TrackingEventInputDTO dto, EventType eventType, HttpServletRequest request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        TrackingEventDocument eventDocument = this.mapper.map(dto, TrackingEventDocument.class);
        eventDocument.setEventType(eventType);
        eventDocument.setTimestamp(new Date());
        if (authentication != null && authentication.isAuthenticated()){
            eventDocument.setUserId(authentication.getName());
        }
        eventDocument.setId(UUID.randomUUID().toString());
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null)ipAddress = request.getRemoteAddr();
        eventDocument.setIpAddress(ipAddress);
        eventDocument.setUserAgent(request.getHeader("user-agent"));
       return eventDocument;
    }


    @GetMapping("/stats/{adId}")
    public DailyStatsOutputDTO getDailyStatsByAdId(@PathVariable String adId){
        return this.mapper.map(this.statsService.getDailyStatsByAdId(adId), DailyStatsOutputDTO.class);
    }

    @PostMapping("/stats/lot")
    public List<DailyStatsOutputDTO> getDailyStatsForLot(@RequestBody List<String> lot){
        return this.statsService.getDailyStatsForLot(lot).stream().map(stats -> this.mapper.map(stats, DailyStatsOutputDTO.class)).toList();
    }




    @ExceptionHandler
    public String exception(Exception e){
        return e.getMessage();
    }
}
