package org.mts.announcesservice.repositories;

import org.mts.announcesservice.entities.AnnounceType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface AnnounceTypeRepository extends JpaRepository<AnnounceType, String>, JpaSpecificationExecutor<AnnounceType> {

    public Page<AnnounceType> findAllByNameContains(String name, Pageable pageable);
    public List<AnnounceType> findAllByCategoryId(String id);
}
