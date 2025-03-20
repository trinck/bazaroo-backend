package org.mts.announcesservice.schedulers;

import org.mts.announcesservice.entities.Announce;
import org.mts.announcesservice.service.IAnnounceSearchService;
import org.mts.announcesservice.service.IAnnounceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AnnounceScheduler implements IAnnounceScheduler {

    private IAnnounceSearchService searchService;

    private IAnnounceService announceService;

    public AnnounceScheduler(IAnnounceSearchService searchService, IAnnounceService announceService) {
        this.searchService = searchService;
        this.announceService = announceService;
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
}
