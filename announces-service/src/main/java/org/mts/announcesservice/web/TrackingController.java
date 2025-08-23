package org.mts.announcesservice.web;

import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.mts.announcesservice.dtos.TrackingEventInputDTO;
import org.mts.announcesservice.enums.AdTrackingType;
import org.mts.announcesservice.remote_entities.TrackingEventDocument;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("tracking")
public class TrackingController {

    private final StreamBridge bridge;
    private final ModelMapper mapper;
    @Value("${app.broker.topics.tracking-in}")
    private String TOPIC_TRACKING_EVENTS_IN;

    public TrackingController(StreamBridge bridge, ModelMapper mapper) {
        this.bridge = bridge;
        this.mapper = mapper;
    }


    @PostMapping("/view")
    public boolean addView(@RequestBody TrackingEventInputDTO dto, HttpServletRequest request) {
        return this.bridge.send(this.TOPIC_TRACKING_EVENTS_IN, initTrackingEvent(dto, AdTrackingType.VIEW, request));

    }

    @PostMapping("/impression")
    public boolean addImpression(@RequestBody TrackingEventInputDTO dto, HttpServletRequest request) {
        return this.bridge.send(this.TOPIC_TRACKING_EVENTS_IN, initTrackingEvent(dto, AdTrackingType.IMPRESSION, request));

    }

    @PostMapping("/share")
    public boolean addShare(@RequestBody TrackingEventInputDTO dto, HttpServletRequest request) {
        return this.bridge.send(this.TOPIC_TRACKING_EVENTS_IN, initTrackingEvent(dto, AdTrackingType.SHARE, request));

    }



    @PostMapping("/click")
    public boolean addClick(@RequestBody TrackingEventInputDTO dto, HttpServletRequest request) {

        return this.bridge.send(this.TOPIC_TRACKING_EVENTS_IN, initTrackingEvent(dto, AdTrackingType.CLICK, request));

    }

    private TrackingEventDocument initTrackingEvent(@RequestBody TrackingEventInputDTO dto, AdTrackingType eventType, HttpServletRequest request) {

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







    @ExceptionHandler
    public String exception(Exception e){
        return e.getMessage();
    }
}
