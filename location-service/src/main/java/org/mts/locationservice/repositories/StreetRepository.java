package org.mts.locationservice.repositories;


import org.mts.locationservice.entities.Street;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StreetRepository extends JpaRepository<Street, String> {
    public Page<Street> findAllByNameContains(String name, Pageable pageable);
    public Page<Street> findAllByCityId(String name, Pageable pageable);
}
