package org.mts.announcesservice.schedulers;

import co.elastic.clients.elasticsearch._types.SortOrder;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.mts.announcesservice.documents.AnnounceDocument;
import org.mts.announcesservice.entities.Announce;
import org.mts.announcesservice.entities.Preference;
import org.mts.announcesservice.entities.SavedSearch;
import org.mts.announcesservice.enums.SavedSearchScheduleType;
import org.mts.announcesservice.repositories.PreferenceRepository;
import org.mts.announcesservice.repositories.SavedSearchRepository;
import org.mts.announcesservice.service.IAnnounceSearchService;
import org.mts.announcesservice.service.IAnnounceService;
import org.mts.announcesservice.service.IPreferenceService;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Component
@Slf4j
public class AnnounceScheduler implements IAnnounceScheduler {

    private final IAnnounceSearchService searchService;
    private final StreamBridge bridge;
    private final IAnnounceService announceService;
    private final SavedSearchRepository savedSearchRepository;
    private final IPreferenceService preferenceService;


    public AnnounceScheduler(IAnnounceSearchService searchService, StreamBridge bridge, IAnnounceService announceService, SavedSearchRepository savedSearchRepository, PreferenceRepository preferenceRepository, IPreferenceService preferenceService) {
        this.searchService = searchService;
        this.bridge = bridge;
        this.announceService = announceService;
        this.savedSearchRepository = savedSearchRepository;
        this.preferenceService = preferenceService;
    }

    /**
     * @return {@code True} if there are many of announces expired or
     * {@code False} if not
     */
    @Override
    public boolean checkAnnouncesExpired() {

        Announce announce;
        return false;
    }

    /**
     * @return
     */
    @Override
    //@Scheduled(cron = "${schedulers.advertScheduler.search-weekly}")
   // @Scheduled(cron = "0 0 0 */7 * *")
    public boolean scheduleSearchWeekly() {
        return true;
    }



   // @Scheduled(cron = "0 8 9 * * *")
    public boolean scheduleSearchDaily() {
        return true;
    }



}
