package org.mts.locationservice.services;

import org.mts.locationservice.configs.CountryContext;
import org.mts.locationservice.entities.Country;
import org.mts.locationservice.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
    public Page<Country> getCountriesPages(Pageable pageable, String search) {
        if (!StringUtils.hasText(search)) {
            return countryRepository.findAll(pageable); // no spec needed
        }
        Specification<Country> spec = buildSearchSpec(search);
        return this.countryRepository.findAll(spec, pageable);
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

    private Specification<Country> buildSearchSpec(String search) {
        if (!StringUtils.hasText(search)) return null; // no filter -> all

        final String like = "%" + search.toLowerCase() + "%";

        return (root, query, cb) -> cb.or(
                cb.like(cb.lower(root.get("id")), like),
                cb.like(cb.lower(root.get("name")), like),
                cb.like(cb.lower(root.get("code")), like),
                cb.like(cb.lower(root.get("telCode")), like),
                cb.like(cb.lower(root.get("currency")), like),
                cb.like(cb.lower(root.get("currencyName")), like)
        );
    }

}
