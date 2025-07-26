package org.mts.notificationservice.consumers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.mts.notificationservice.entities.Notification;
import org.mts.notificationservice.enums.NotificationAudience;
import org.mts.notificationservice.enums.NotificationTargetType;
import org.mts.notificationservice.enums.NotificationType;
import org.mts.notificationservice.services.INotificationService;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Consumer;

@Component
@Slf4j
public class NotificationsConsumer {


    private final INotificationService notificationService;

    public NotificationsConsumer(INotificationService notificationService, SimpMessageSendingOperations messagingTemplate, ModelMapper modelMapper) {
        this.notificationService = notificationService;
    }

    @Bean
    public Consumer<Map<String, Object>> notifsConsumer() {

        return (notification) -> {
            String userId = notification.get("userId") == null ? null : (String) notification.get("userId");
            Map<String, Object> data = notification.get("data") == null? null: (Map<String, Object>)notification.get("data");
            String message = (String) Objects.requireNonNull(notification.get("message"));
            String service = (String) Objects.requireNonNull(notification.get("service"));
            NotificationAudience audience = NotificationAudience.valueOf((String) Objects.requireNonNull(notification.get("audience")));
            NotificationTargetType targetType = NotificationTargetType.valueOf((String) Objects.requireNonNull(notification.get("targetType")));
            String type = (String)Objects.requireNonNull(notification.get("type"));
            String title = (String)Objects.requireNonNull(notification.get("title"));
            String dataToSave = null;
            try {
                if (data != null) {
                    dataToSave = new ObjectMapper().writeValueAsString(data);
                }
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            Notification notificationToSave = Notification.builder()
                    .seen(false)
                    .clicked(false)
                    .service(service)
                    .title(title)
                    .audience(audience)
                    .targetType(targetType)
                    .userId(userId)
                    .data(dataToSave)
                    .message(message)
                    .type(type)
                    .build();

            switch (targetType) {
                case PRIVATE -> {
                    this.notificationService.notifyUser(userId, "/queue", notificationToSave);
                }
                case GLOBAL -> {
                    notificationToSave.setUserId(null);
                    switch (audience) {
                        case USERS -> {
                            this.notificationService.notifyAll("topic/users", notificationToSave);
                        }
                        case ADMINS -> {
                            this.notificationService.notifyAll("topic/admins", notificationToSave);
                        }

                        case ALL -> {
                            this.notificationService.notifyAll("topic/all", notificationToSave);
                        }
                    }
                }
            }


        };


    }


}
