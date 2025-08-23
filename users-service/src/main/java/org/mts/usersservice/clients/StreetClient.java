package org.mts.usersservice.clients;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.mts.usersservice.remote_entities.Street;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "LOCATION-SERVICE")

public interface StreetClient {

    @GetMapping("/streets/{id}")
    @CircuitBreaker(name = "streetBreaker", fallbackMethod = "getDefaultStreet")
    public Street getStreet(@PathVariable("id") String id);

    default Street getDefaultStreet(String id, Throwable throwable){
        return Street.builder()
                .zip(0L)
                .name("undefined")
                .id("undefined")
                .cityId("undefined")
                .build();

    }
}
