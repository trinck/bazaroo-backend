package org.mts.trackingservice.web;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.mts.trackingservice.documents.TrackingEventDocument;
import org.mts.trackingservice.dtos.DailyStatsOutputDTO;
import org.mts.trackingservice.dtos.TrackingEventInputDTO;
import org.mts.trackingservice.enums.EventType;
import org.mts.trackingservice.services.IRedisService;
import org.mts.trackingservice.services.IStatsService;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("tracking-events")
public class EventsController {


    private StreamBridge bridge;
    private ModelMapper mapper;
    private IStatsService statsService;
    public EventsController(StreamBridge bridge, ModelMapper mapper,  IStatsService statsService) {
        this.bridge = bridge;
        this.mapper = mapper;
        this.statsService = statsService;
    }


    @PostMapping("/view")
    public boolean addView(@RequestBody TrackingEventInputDTO dto, HttpServletRequest request) {
        return this.bridge.send("tracking-events", initTrackingEvent(dto, EventType.VIEW, request));

    }

    @PostMapping("/impression")
    public boolean addImpression(@RequestBody TrackingEventInputDTO dto, HttpServletRequest request) {
        return this.bridge.send("tracking-events", initTrackingEvent(dto, EventType.IMPRESSION, request));

    }

    @PostMapping("/share")
    public boolean addShare(@RequestBody TrackingEventInputDTO dto, HttpServletRequest request) {
        return this.bridge.send("tracking-events", initTrackingEvent(dto, EventType.SHARE, request));

    }



    @PostMapping("/click")
    public boolean addClick(@RequestBody TrackingEventInputDTO dto, HttpServletRequest request) {


        return this.bridge.send("tracking-events", initTrackingEvent(dto, EventType.CLICK, request));

    }

    private TrackingEventDocument initTrackingEvent(@RequestBody TrackingEventInputDTO dto, EventType eventType, HttpServletRequest request) {
        TrackingEventDocument eventDocument = this.mapper.map(dto, TrackingEventDocument.class);
        eventDocument.setEventType(eventType);
        eventDocument.setTimestamp(new Date());
        eventDocument.setUserId(UUID.randomUUID().toString());
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
