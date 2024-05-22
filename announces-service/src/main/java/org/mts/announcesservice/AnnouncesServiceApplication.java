package org.mts.announcesservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AnnouncesServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnnouncesServiceApplication.class, args);
    }

}
