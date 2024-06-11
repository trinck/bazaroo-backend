package org.mts.imagesservice.repositories;


import org.mts.imagesservice.entities.Media;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MediaRepository extends JpaRepository<Media,Long> {

    public Page<Media> findByNameContains(String name, Pageable pageable);
    public Page<Media> findByTypeContains(String type, Pageable pageable);
    public List<Media> findAllByPathContains(String path);
}
