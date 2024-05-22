package org.mts.announcesservice.repositories;

import org.mts.announcesservice.entities.CheckUnit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckUnitRepository extends JpaRepository<CheckUnit, Long> {


    public Page<CheckUnit> findAllByNameContains(String name, Pageable pageable);
}
