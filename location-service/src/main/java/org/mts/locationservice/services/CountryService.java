package org.mts.locationservice.services;

import org.mts.locationservice.configs.CountryContext;
import org.mts.locationservice.entities.Country;
import org.mts.locationservice.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CountryService implements ICountryService {

    @Autowired
    private CountryRepository countryRepository;


    /**
     * @return
     */
    @Override
    public List<Country> getCountries() {
        return this.countryRepository.findAll();
    }

    /**
     * @param pageable
     * @return
     */
    @Override
    public Page<Country> getCountriesPages(Pageable pageable) {
        return this.countryRepository.findAll(pageable);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Country getCountryById(String id) {
        return this.countryRepository.findById(id).orElseThrow();
    }

    /**
     * @param country
     * @return
     */
    @Override
    public Country save(Country country) {
        return this.countryRepository.save(country);
    }

    /**
     * @param country
     * @return
     */
    @Override
    public Country update(Country country) {
        return this.countryRepository.save(country);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Country deleteCountryById(String id) {

        Country country = this.countryRepository.findById(id).orElseThrow();
        this.countryRepository.deleteById(id);
        return country;
    }

    /**
     * @param name
     * @return
     */
    @Override
    public Country getCountryByName(String name) {
        return this.countryRepository.findByNameIgnoreCase(name).orElseThrow();
    }

    /**
     * @param code
     * @param pageable
     * @return
     */
    @Override
    public Page<Country> getCountriesByCode(String code, Pageable pageable) {
        return this.countryRepository.findAllByCodeContains(code, pageable);
    }

    /**
     * @param code
     * @return
     */
    @Override
    public Country getCountryByCode(String code) {
        return this.countryRepository.findCountryByCodeIgnoreCase(code).orElseThrow();
    }


    /**
     *
     * @return
     */
    @Override
    public Country getCountryByCode() {
        return this.countryRepository.findCountryByCodeIgnoreCase(CountryContext.getCountry()).orElseThrow();
    }
}
