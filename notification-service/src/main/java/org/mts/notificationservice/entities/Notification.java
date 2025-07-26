package org.mts.notificationservice.entities;


import jakarta.persistence.*;
import lombok.*;
import org.mts.notificationservice.enums.NotificationAudience;
import org.mts.notificationservice.enums.NotificationTargetType;
import org.mts.notificationservice.enums.NotificationType;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
    private String url;
    private String userId;
    private String service;
    @Enumerated(EnumType.STRING)
    private NotificationAudience audience;
    private String title;
    @Enumerated(EnumType.STRING)
    private NotificationTargetType targetType;
    private boolean seen;
    private boolean clicked;
    @Column(length = 1000)
    private String data;
    private String type;
    private Date createdAt = new Date();


    @PrePersist
    public void prePersist() {
        this.createdAt = new Date();
    }
}
