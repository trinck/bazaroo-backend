package org.mts.announcesservice.service;

import org.mts.announcesservice.entities.CategoryField;
import org.mts.announcesservice.repositories.CategoryFieldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryFieldService implements ICategoryFieldService{


    @Autowired
    private CategoryFieldRepository repository;

    /**
     * @param categoryField
     * @return
     */
    @Override
    public CategoryField create(CategoryField categoryField) {
        return this.repository.save(categoryField);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public CategoryField deleteById(Long id) {

        CategoryField categoryField = this.repository.findById(id).orElseThrow();
       this.repository.deleteById(id);
        return categoryField;
    }

    /**
     * @param id
     * @return
     */
    @Override
    public CategoryField getByID(Long id) {
        return this.repository.findById(id).orElseThrow();
    }

    /**
     * @param categoryField
     * @return
     */
    @Override
    public CategoryField update(CategoryField categoryField) {
        return this.repository.save(categoryField);
    }

    /**
     * @param pageable
     * @return
     */
    @Override
    public Page<CategoryField> getCategoryFields(Pageable pageable) {
        return this.repository.findAll(pageable);
    }

    /**
     * @return
     */
    @Override
    public List<CategoryField> getAll() {
        return this.repository.findAll();
    }

    /**
     * @param fieldName
     * @param pageable
     * @return
     */
    @Override
    public Page<CategoryField> getByFieldName(String fieldName, Pageable pageable) {
        return this.repository.findAllByFieldNameContains(fieldName, pageable);
    }
}
