package org.mts.locationservice.services;

import org.mts.locationservice.entities.City;
import org.mts.locationservice.entities.Country;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICityService {


    public List<City> getCities();
    public Page<City> getCitiesPages(Pageable pageable);
    public City getCityById(String id);
    public City saveCity(City city);
    public City updateCity(City city);
    public City deleteCiyById(String id);
    Page<City> getCitiesByName(String name, Pageable pageable);
}
