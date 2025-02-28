package org.mts.trackingservice.repositories;

import org.mts.trackingservice.entities.DailyStats;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface DailyStatsRepository extends JpaRepository<DailyStats,Long> {

    Optional<DailyStats> findByAdId(String adId);
    List<DailyStats> findAllByAdIdIn(List<String> adIds);
}
