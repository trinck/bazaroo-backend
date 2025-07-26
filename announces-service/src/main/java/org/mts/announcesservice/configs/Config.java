package org.mts.announcesservice.configs;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.oauth2.jwt.JwtDecoder;


@Configuration
@EnableScheduling
public class Config {

    @Bean
    ModelMapper modelMapper(){
        return new ModelMapper();
    }

}
