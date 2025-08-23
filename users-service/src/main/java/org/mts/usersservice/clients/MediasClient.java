package org.mts.usersservice.clients;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.mts.usersservice.remote_entities.Media;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@FeignClient(name = "IMAGES-SERVICE")
public interface MediasClient {


    @GetMapping("/mediaStore/adverts/{announceId}")
    @CircuitBreaker(name = "mediaBreaker", fallbackMethod = "getDefaultMedias")
    public List<Media> getAdvertMedias(@PathVariable("announceId") String announceId);

    default List<Media> getDefaultMedias(String announceId, Throwable throwable){
        return new ArrayList<>();

    }
}
