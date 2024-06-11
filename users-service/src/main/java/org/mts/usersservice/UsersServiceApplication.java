package org.mts.usersservice;

import org.mts.usersservice.configs.DefaultClientPref;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({DefaultClientPref.class})
public class UsersServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UsersServiceApplication.class, args);
    }

}
