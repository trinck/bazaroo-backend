package org.mts.locationservice.services;

import org.mts.locationservice.entities.Country;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICountryService {


    public List<Country> getCountries();
    public Page<Country> getCountriesPages(Pageable pageable, String search);
    public Country getCountryById(String id);
    public Country save(Country country);
    public Country update(Country country);
    public Country deleteCountryById(String id);
    public Country getCountryByName(String name);
    public Page<Country> getCountriesByCode(String code, Pageable pageable);

    public Country getCountryByCode(String code);
    public Country getCountryByCode();
}
