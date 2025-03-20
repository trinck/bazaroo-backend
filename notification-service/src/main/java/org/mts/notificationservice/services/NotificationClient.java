package org.mts.notificationservice.services;

import org.mts.notificationservice.entities.Notification;
import org.springframework.stereotype.Service;

@Service
public class NotificationClient implements INotificationClient {
    /**
     *
     * @param notification notification to send
     */
    @Override
    public void sendToUser( Notification notification) {

    }
}
