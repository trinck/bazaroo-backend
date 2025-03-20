package org.mts.notificationservice.services;

import org.mts.notificationservice.entities.Notification;

import java.util.List;
import java.util.Map;

public class RedisService implements IRedisService {
    /**
     * @param userId
     * @return
     */
    @Override
    public List<Notification> getNotifications(String userId) {
        return List.of();
    }

    /**
     * @return
     */
    @Override
    public Map<String, List<Notification>> getAllNotifications() {
        return Map.of();
    }

    /**
     * @param notification
     */
    @Override
    public void enqueueNotification(Notification notification) {

    }
}
