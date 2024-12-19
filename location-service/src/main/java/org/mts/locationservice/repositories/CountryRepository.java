package org.mts.locationservice.repositories;

import org.mts.locationservice.entities.Country;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country, String> {

    public Page<Country> findAllByCodeContains(String code, Pageable pageable);

    public Optional<Country> findByNameIgnoreCase(String name);
}
