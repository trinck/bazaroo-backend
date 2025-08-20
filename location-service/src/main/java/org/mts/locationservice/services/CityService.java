package org.mts.locationservice.services;

import org.mts.locationservice.entities.City;
import org.mts.locationservice.entities.Country;
import org.mts.locationservice.repositories.CityRepository;
import org.mts.locationservice.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
     * @return
     */
    @Override
    public List<City> getCitiesByCountryName(String countryName) {
        Country country = this.countryRepository.findByNameIgnoreCase(countryName).orElseThrow();
        return this.cityRepository.findAllByCountryId(country.getId());
    }

    /**
     * @param pageable
     * @return
     */
    @Override
    public Page<City> getCitiesPages(Pageable pageable, String search) {
        if (!StringUtils.hasText(search)) {
            return cityRepository.findAll(pageable); // no spec needed
        }
        Specification<City> spec = buildSearchSpec(search);
        return this.cityRepository.findAll(spec, pageable);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public City getCityById(String id) {
        return this.cityRepository.findById(id).orElseThrow();
    }

    /**
     * @param city
     * @return
     */
    @Override
    public City saveCity(City city) {
        return this.cityRepository.save(city);
    }

    /**
     * @param city
     * @return
     */
    @Override
    public City updateCity(City city) {
        return this.cityRepository.save(city);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public City deleteCiyById(String id) {
        City city = this.cityRepository.findById(id).orElseThrow();
        this.cityRepository.deleteById(id);
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

    private Specification<City> buildSearchSpec(String search) {
        if (!StringUtils.hasText(search)) return null; // no filter -> all

        final String like = "%" + search.toLowerCase() + "%";

        return (root, query, cb) -> cb.or(
                cb.like(cb.lower(root.get("id")), like),
                cb.like(cb.lower(root.get("name")), like)
        );
    }

}
