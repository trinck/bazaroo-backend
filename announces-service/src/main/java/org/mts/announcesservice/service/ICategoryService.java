package org.mts.announcesservice.service;

import org.mts.announcesservice.entities.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICategoryService {

    public Category create(Category category);
    public Category deleteById(String id);
    public Category getByID(String id);
    public Category update(Category category);
    public Page<Category> getCategories(Pageable pageable);
    public List<Category> getAll();
    public Page<Category> getByName(String title, Pageable pageable);
}
