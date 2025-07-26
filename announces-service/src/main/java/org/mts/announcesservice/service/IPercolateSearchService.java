package org.mts.announcesservice.service;

import org.mts.announcesservice.documents.AnnounceDocument;

import java.util.List;

public interface IPercolateSearchService {
    public List<String> findMatchingUsers(AnnounceDocument ad);
}
