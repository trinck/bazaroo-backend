package org.mts.notificationservice.services;

import org.mts.notificationservice.entities.Notification;

public interface INotificationClient {

    public void sendToUser(Notification notification);
}
