package org.mts.notificationservice.repositories;

import org.mts.notificationservice.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Notification findByUserId(String userId);

    List<Notification> findAllByUserId(String userId);

    List<Notification> findAllByUserIdAndSeen(String userId, boolean seen);
}