package org.mts.imagesservice;

import org.modelmapper.ModelMapper;
import org.mts.imagesservice.configs.StorageMediasSources;
import org.mts.imagesservice.services.IServiceStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:app.properties")
@SpringBootApplication
@EnableConfigurationProperties({StorageMediasSources.class})
public class ImagesServiceApplication implements CommandLineRunner {


    @Autowired
    private IServiceStorage serviceStorage;

    public static void main(String[] args) {
        SpringApplication.run(ImagesServiceApplication.class, args);
    }


    /**
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {

        serviceStorage.initRootsStorage();

    }
}
