package org.mts.locationservice.repositories;

import org.mts.locationservice.entities.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface CityRepository extends JpaRepository<City,String>, JpaSpecificationExecutor<City> {
   public Page<City> findAllByNameContains(String name, Pageable pageable);
   public List<City> findAllByCountryId(String countryId);
}
