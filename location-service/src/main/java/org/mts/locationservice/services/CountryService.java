package org.mts.locationservice.services;

import org.mts.locationservice.entities.Country;
import org.mts.locationservice.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
public class CountryService implements ICountryService{

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
        return this.countryRepository.findById(id).orElse(null);
    }

    /**
     * @param country
     * @return
     */
    @Override
    public Country addCountry(Country country) {
        return this.countryRepository.save(country);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Country deleteCountryById(String id) {

        Country country = null;
        if(this.countryRepository.existsById(id)){

            country = this.countryRepository.findById(id).orElse(null);
            this.countryRepository.deleteById(id);
        }
        return country;
    }

    /**
     * @param name
     * @return
     */
    @Override
    public Page<Country> getCountryByName(String name, Pageable pageable) {

        return this.countryRepository.findByNameContains(name, pageable);
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
}
