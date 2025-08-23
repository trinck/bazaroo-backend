package org.mts.locationservice.services;

import org.mts.locationservice.entities.City;
import org.mts.locationservice.entities.Street;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IStreetService {

    public List<Street> getStreets();
    public Page<Street> getStreetsPages(Pageable pageable, String search);
    public Street getStreetById(String id);
    public Street saveStreet(Street street);
    public Street updateStreet(Street street);
    public Street deleteStreetById(String id);
    Page<Street> getStreetsByCityId(String id, Pageable pageable);
}
