package org.mts.announcesservice.service;

import co.elastic.clients.elasticsearch._types.SortOrder;

import java.io.IOException;
import java.util.Map;

public interface IAnnounceSearchService {
    public Map<String, Object> searchAnnounces(String query, String category, String sortBy, SortOrder order, int page, int size) throws IOException;
    public Map<String, Long> getCategoryCounts();
}
