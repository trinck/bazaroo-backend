package org.mts.imagesservice.configs;

import com.cloudinary.Cloudinary;
import org.modelmapper.ModelMapper;
import org.mts.imagesservice.services.IServiceStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class Config {


    @Value("${CLOUDINARY_URL}")
    private String cloudinaryUrl;

    @Bean
    ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    Cloudinary cloudinary(){
        return new Cloudinary(cloudinaryUrl);
    }

}
