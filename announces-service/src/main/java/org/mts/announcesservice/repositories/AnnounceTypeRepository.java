package org.mts.announcesservice.repositories;

import org.mts.announcesservice.entities.AnnounceType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnnounceTypeRepository extends JpaRepository<AnnounceType, String> {

    public Page<AnnounceType> findAllByNameContains(String name, Pageable pageable);
    public List<AnnounceType> findAllByCategoryId(String id);
}
