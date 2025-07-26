package org.mts.notificationservice.repositories;

import org.mts.notificationservice.entities.Notification;
import org.mts.notificationservice.enums.NotificationAudience;
import org.mts.notificationservice.enums.NotificationTargetType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Notification findByUserId(String userId);

    List<Notification> findAllByUserId(String userId);

    List<Notification> findAllByUserIdAndSeen(String userId, boolean seen);

    Optional<Notification> findByIdAndUserId(Long id, String userId);

    List<Notification> findAllByUserIdAndSeenFalse(String userId);

    List<Notification> findByAudienceEqualsAndTargetTypeEquals(NotificationAudience audience, NotificationTargetType targetType);
}