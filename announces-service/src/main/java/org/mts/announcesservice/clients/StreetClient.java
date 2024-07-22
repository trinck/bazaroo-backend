package org.mts.announcesservice.clients;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.mts.announcesservice.remote_entities.Street;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "LOCATION-SERVICE")

public interface StreetClient {

    @GetMapping("/streets/{id}")
    @CircuitBreaker(name = "mediaBreaker", fallbackMethod = "getDefaultMedias")
    public Street getStreet(@PathVariable String id);

    default Street getDefaultStreet(String id, Throwable throwable){
        return Street.builder()
                .zip(0L)
                .locations(null)
                .name("undefined")
                .id("undefined")
                .cityId("undefined")
                .build();

    }
}
