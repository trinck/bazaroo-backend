package org.mts.announcesservice.repositories;

import org.mts.announcesservice.entities.SavedSearch;
import org.mts.announcesservice.enums.SavedSearchScheduleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SavedSearchRepository extends JpaRepository<SavedSearch, Long> {
   public List<SavedSearch> findSavedSearchesByActiveAndType(boolean active, SavedSearchScheduleType type);
    public void deleteAllByUserId(String userId);

   public Optional<SavedSearch> findByIdAndUserId(Long id, String userId);

    List<SavedSearch> findAllByUserId(String userId);
}