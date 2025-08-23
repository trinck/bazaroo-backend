package org.mts.announcesservice.configs;

import org.hibernate.collection.spi.PersistentCollection;
import org.modelmapper.Condition;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.oauth2.jwt.JwtDecoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Configuration
@EnableScheduling
public class Config {

    @Bean
    ModelMapper modelMapper(){


        return new ModelMapper();
    }

}
