package org.mts.announcesservice.service;

import org.mts.announcesservice.entities.Announce;
import org.mts.announcesservice.entities.CategoryField;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface ICategoryFieldService {


    public CategoryField create(CategoryField categoryField);
    public CategoryField deleteById(Long id);
    public CategoryField getByID(Long id);
    public CategoryField update(CategoryField categoryField);
    public Page<CategoryField> getCategoryFields(Pageable pageable, String search);
    public List<CategoryField> getAll();
    public Page<CategoryField> getByFieldName(String fieldName, Pageable pageable);
}
