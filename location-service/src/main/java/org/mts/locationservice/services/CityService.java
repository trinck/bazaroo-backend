package org.mts.locationservice.services;

import org.mts.locationservice.entities.City;
import org.mts.locationservice.entities.Country;
import org.mts.locationservice.repositories.CityRepository;
import org.mts.locationservice.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService implements ICityService{


    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private CountryRepository countryRepository;


    /**
     * @return
     */
    @Override
    public List<City> getCities() {
        return this.cityRepository.findAll();
    }

    /**
     * @param pageable
     * @return
     */
    @Override
    public Page<City> getCitiesPages(Pageable pageable) {
        return this.cityRepository.findAll(pageable);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public City getCityById(String id) {
        return this.cityRepository.findById(id).orElse(null);
    }

    /**
     * @param city
     * @return
     */
    @Override
    public City addCity(City city) {
        return this.cityRepository.save(city);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public City deleteCiyById(String id) {

        City city = null;
        if(this.cityRepository.existsById(id)){
            city = this.cityRepository.findById(id).orElse(null);
            this.cityRepository.deleteById(id);

        }
        return city;
    }

    /**
     * @param name
     * @param pageable
     * @return
     */
    @Override
    public Page<City> getCitiesByName(String name, Pageable pageable) {
        return this.cityRepository.findAllByNameContains(name, pageable);
    }
}
