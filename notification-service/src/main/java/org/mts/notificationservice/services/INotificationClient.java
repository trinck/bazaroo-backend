package org.mts.notificationservice.services;

import org.mts.notificationservice.entities.Notification;

public interface INotificationClient {

    public void sendToUser(String userId,Notification notification);
    public void sendToAll(Notification notification);
}
