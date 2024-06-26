package org.mts.announcesservice.repositories;

import org.mts.announcesservice.entities.CategoryField;
import org.mts.announcesservice.entities.Field;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryFieldRepository extends JpaRepository<CategoryField, Long> {

    public Page<CategoryField> findAllByFieldNameContains(String fieldName, Pageable pageable);
    public List<CategoryField> findAllByAnnounceTypeId(String id);
}
