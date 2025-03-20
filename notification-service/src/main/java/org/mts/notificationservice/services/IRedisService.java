package org.mts.notificationservice.services;

import org.mts.notificationservice.entities.Notification;

import java.util.List;
import java.util.Map;

public interface IRedisService {
    public List<Notification> getNotifications(String userId);
    public Map<String,List<Notification>> getAllNotifications();
    public void enqueueNotification(Notification notification) ;

}
