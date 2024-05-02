package org.mts.imagesservice.configs;

import org.modelmapper.ModelMapper;
import org.mts.imagesservice.services.IServiceStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {



    @Bean
    ModelMapper modelMapper(){
        return new ModelMapper();
    }

}
