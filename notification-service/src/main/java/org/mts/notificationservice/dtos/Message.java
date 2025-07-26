package org.mts.notificationservice.dtos;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import org.mts.notificationservice.enums.NotificationAudience;
import org.mts.notificationservice.enums.NotificationTargetType;
import org.mts.notificationservice.enums.NotificationType;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class Message {

    private Long id;
    private String userId;
    private String message;
    private String type;
    private NotificationAudience audience;
    private NotificationTargetType targetType;
    private boolean seen;
    private String url;
    private boolean clicked;
    private String data;
    private Date createdAt;
    private String service;
    private String title;

}
