package org.mts.locationservice.clients;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.mts.locationservice.remote_entities.Announce;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface AnnounceRestClient {


    @GetMapping("/ANNOUNCE-SERVICE/announces/index/{id}")
    @CircuitBreaker(name = "announceService", fallbackMethod = "getDefaultAnnounce")
    public Announce getAnnounceById(@PathVariable Long id);

    default Announce getDefaultAnnounce(String id){

        return Announce.builder()
                .id(id)
                .title("Not available")
                .price(null)
                .description("Not available")
                .address("Not available")
                .status("Not available")
                .build();

    }
}
