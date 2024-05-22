package org.mts.mailservice;

import org.mts.mailservice.configs.MailAuthConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication

@PropertySource("classpath:app.properties")
@EnableConfigurationProperties({MailAuthConfig.class})
public class MailServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MailServiceApplication.class, args);
    }

}
