package org.mts.announcesservice.service;

import co.elastic.clients.elasticsearch._types.SortOrder;
import org.mts.announcesservice.dtos.SavedSearchInputDto;
import org.mts.announcesservice.dtos.SavedSearchRequest;
import org.mts.announcesservice.dtos.SearchRequestDTO;
import org.mts.announcesservice.entities.SavedSearch;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

public interface IAnnounceSearchService {
    public Map<String, Object> searchAnnounces(String query, String category, String sortBy, SortOrder order, int page, int size, Date dateBegin, String userId, String tenantId) throws IOException;

    Map<String, Object> searchAnnounces(SearchRequestDTO searchRequest) throws IOException;

    public Map<String, Long> getCategoryCounts();
    public SavedSearch saveSearch(SavedSearchInputDto searchToSave);
    public SavedSearch saveSearch(SavedSearchRequest searchToSave);
    public SavedSearch update(SavedSearch savedSearch);
    public void deleteAllSavedSearchByUserId(String userId);
    public SavedSearch getSavedSearchByIdAndUserId(Long id, String userId);

}
