package org.mts.notificationservice.services;

import org.modelmapper.ModelMapper;
import org.mts.notificationservice.entities.Notification;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Service
public class NotificationClient implements INotificationClient {

    private final SimpMessageSendingOperations messagingTemplate;
    private final ModelMapper modelMapper;

    public NotificationClient(SimpMessageSendingOperations messagingTemplate, ModelMapper modelMapper) {
        this.messagingTemplate = messagingTemplate;
        this.modelMapper = modelMapper;
    }

    /**
     *
     * @param notification notification to send
     */
    @Override
    public void sendToUser( String userId,Notification notification) {

    }

    /**
     * @param notification
     */
    @Override
    public void sendToAll(Notification notification) {

    }
}
