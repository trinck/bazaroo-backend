package org.mts.locationservice.services;

import org.mts.locationservice.entities.Street;
import org.mts.locationservice.repositories.StreetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StreetService implements IStreetService{

    @Autowired
   private StreetRepository streetRepository;

    /**
     * @return
     */
    @Override
    public List<Street> getStreets() {
      return this.streetRepository.findAll();
    }

    /**
     * @param pageable
     * @return
     */
    @Override
    public Page<Street> getStreetsPages(Pageable pageable) {
        return this.streetRepository.findAll(pageable);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Street getStreetById(String id) {
        return this.streetRepository.findById(id).orElseThrow();
    }

    /**
     * @param street
     * @return
     */
    @Override
    public Street saveStreet(Street street) {
        return this.streetRepository.save(street);
    }

    /**
     * @param street
     * @return
     */
    @Override
    public Street updateStreet(Street street) {
        return this.streetRepository.save(street);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Street deleteStreetById(String id) {

        Street street = this.streetRepository.findById(id).orElseThrow();
        this.streetRepository.deleteById(id);
        return street;
    }

    /**
     * @param id
     * @param pageable
     * @return
     */
    @Override
    public Page<Street> getStreetsByCityId(String id, Pageable pageable) {
        return this.streetRepository.findAllByCityId(id, pageable);
    }
}
