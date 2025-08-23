package org.mts.imagesservice.consumers;

import lombok.extern.slf4j.Slf4j;
import org.mts.imagesservice.services.IMediaService;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Consumer;

@Component
@Slf4j
public class AdvertsConsumers {




    private final IMediaService mediaService;

    public AdvertsConsumers(IMediaService mediaService) {
        this.mediaService = mediaService;
    }


    @Bean
    public Consumer<Map<String,Object>> adCreateError(){

        return (input)->{
            String adId = input.get("adId").toString();
            log.info("deleting images for announce {} if exist ...",adId);
            this.mediaService.deleteAllByAdId(adId);
            log.info("deleted images for announce {}",adId);
        };
    }
}
