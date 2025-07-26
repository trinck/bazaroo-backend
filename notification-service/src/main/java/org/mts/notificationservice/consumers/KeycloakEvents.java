package org.mts.notificationservice.consumers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
@Slf4j
public class KeycloakEvents {


    @Bean
    public Consumer<String> userEvents (){
        return log::info;
    }

    @Bean
    public Consumer<String> adminEvents(){
        return log::info;
    }
}
