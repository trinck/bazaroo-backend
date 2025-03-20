package org.mts.notificationservice.consumers;


import lombok.extern.slf4j.Slf4j;
import org.mts.notificationservice.entities.Notification;
import org.mts.notificationservice.services.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

@Component
@Slf4j
public class NotificationClientsConsumer {


    @Autowired
    private INotificationService notificationService;

    @Bean
    public Consumer<Map<String, Object>> searchAdAlert() {

        return (notification) -> {
            Object adsSource = Objects.requireNonNull(notification.get("ads"));
            List<String> ads = new ArrayList<>();
            if (adsSource instanceof List<?>) {
                for (Object ad : (List<?>) adsSource) {
                    if (ad instanceof String) {
                        ads.add((String) ad);
                    }
                }
            }



        };


    }
}
