package org.mts.announcesservice.repositories;

import org.mts.announcesservice.entities.Field;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FieldRepository extends JpaRepository<Field,String> {

    public Page<Field> findAllByNameContains(String name, Pageable pageable);
    public List<Field> findByAnnounceId(String id);
}
