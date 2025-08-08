package org.mts.announcesservice.schedulers;

import co.elastic.clients.elasticsearch._types.SortOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.mts.announcesservice.documents.AnnounceDocument;
import org.mts.announcesservice.dtos.SavedSearchRequest;
import org.mts.announcesservice.dtos.SearchRequestDTO;
import org.mts.announcesservice.entities.Preference;
import org.mts.announcesservice.entities.SavedSearch;
import org.mts.announcesservice.enums.NotificationAudience;
import org.mts.announcesservice.enums.NotificationTargetType;
import org.mts.announcesservice.enums.NotificationType;
import org.mts.announcesservice.enums.SavedSearchScheduleType;
import org.mts.announcesservice.repositories.SavedSearchRepository;
import org.mts.announcesservice.service.IAnnounceSearchService;
import org.mts.announcesservice.service.IPreferenceService;
import org.mts.announcesservice.service.ISchedulerService;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
public class SearchJob implements Job {

    private  IAnnounceSearchService searchService;
    private  StreamBridge bridge;
    private  SavedSearchRepository savedSearchRepository;
    private  IPreferenceService preferenceService;
    private  ISchedulerService schedulerService;
    @Value("${app.broker.topics.notification}")
    private  String NOTIFICATION_TOPIC;
    @Value("${app.clients.front.urls.advert.search}")
    private String ADVERT_SEARCH_URL;
    @Value("${spring.application.name}")
    private String APPLICATION_NAME;



    public SearchJob(IAnnounceSearchService searchService, StreamBridge bridge, SavedSearchRepository savedSearchRepository, IPreferenceService preferenceService, ISchedulerService schedulerService) {
        super();
        this.searchService = searchService;
        this.bridge = bridge;
        this.savedSearchRepository = savedSearchRepository;
        this.preferenceService = preferenceService;
        this.schedulerService = schedulerService;
    }

    /**
     * @param jobExecutionContext
     * @throws JobExecutionException
     */
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap data = jobExecutionContext.getMergedJobDataMap();
        String userId = data.getString("userId");
        Long id = data.getLong("id");
        SavedSearch savedSearch = this.savedSearchRepository.findById(id).orElse(null);

        try {
             Preference preference = this.preferenceService.getPreferenceByUserId(userId);
            if (savedSearch == null || savedSearch.getType() == SavedSearchScheduleType.IMMEDIATE || !savedSearch.getActive()) {
                this.schedulerService.deleteNotification(id);
                return;
            }

            if (!preference.getEnabled()) {
                savedSearch.setActive(false);
                this.searchService.update(savedSearch);
                return;
            }


            Map<String, Object> response = this.searchService.searchAnnounces(new ObjectMapper().readValue(savedSearch.getCriteria(), SearchRequestDTO.class));

            long totalElements = (long) response.get("totalElements");
            if (totalElements == 0){
                return;
            }

            Map<String, Object> meta = new HashMap<>();
            meta.put("total", totalElements);
            meta.put("adIds",((List<AnnounceDocument>) response.get("content")).stream().map(AnnounceDocument::getId).toList());
            meta.put("criteria",savedSearch.getCriteria());

            this.bridge.send(this.NOTIFICATION_TOPIC, Map.of("userId", userId, "message", String.format("%d nouvelle(s) annonce(s) disponible(s) pour vous", totalElements), "type", NotificationType.ANNOUNCES_MATCH, "targetType", NotificationTargetType.PRIVATE, "audience", NotificationAudience.ALL,"service", this.APPLICATION_NAME, "data",meta, "title", "Matchs"));
            this.updateSavedSearch(savedSearch);


        } catch (JsonProcessingException | SchedulerException | NoSuchElementException e) {
            if (e instanceof NoSuchElementException) {
                try {
                    this.schedulerService.deleteNotification(id);
                } catch (SchedulerException ex) {
                    throw new RuntimeException(ex);
                }
            }
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }


    private void updateSavedSearch(SavedSearch savedSearch) {
        savedSearch.setBeginDate(new Date());
        this.searchService.update(savedSearch);
    }
}
