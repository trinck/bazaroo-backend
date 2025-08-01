package org.mts.locationservice.repositories;

import org.mts.locationservice.entities.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CityRepository extends JpaRepository<City,String> {
   public Page<City> findAllByNameContains(String name, Pageable pageable);
   public List<City> findAllByCountryId(String countryId);
}
