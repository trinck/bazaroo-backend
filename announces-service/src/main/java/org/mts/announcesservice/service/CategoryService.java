package org.mts.announcesservice.service;

import org.mts.announcesservice.entities.Category;
import org.mts.announcesservice.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService implements  ICategoryService{

    @Autowired
  private CategoryRepository repository;
    /**
     * @param category
     * @return
     */
    @Override
    public Category create(Category category) {
        return this.repository.save(category);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Category deleteById(String id) {

        Category category = this.repository.findById(id).orElseThrow();
        this.repository.deleteById(id);
        return category;
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Category getByID(String id) {
        return this.repository.findById(id).orElseThrow();
    }

    /**
     * @param category
     * @return
     */
    @Override
    public Category update(Category category) {
        return this.repository.save(category);
    }

    /**
     * @param pageable
     * @return
     */
    @Override
    public Page<Category> getCategories(Pageable pageable) {
        return this.repository.findAll(pageable);
    }

    /**
     * @return
     */
    @Override
    public List<Category> getAll() {
        return this.repository.findAll();
    }

    /**
     * @param title
     * @param pageable
     * @return
     */
    @Override
    public Page<Category> getByName(String title, Pageable pageable) {
        return this.repository.findAllByTitleContains(title, pageable);
    }
}
