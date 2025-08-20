package org.mts.announcesservice.service;

import org.mts.announcesservice.entities.Announce;
import org.mts.announcesservice.entities.Category;
import org.mts.announcesservice.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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
    @Transactional
    public Category deleteById(String id) {
        Category category = this.repository.findById(id).orElseThrow();

        // ⚠️ Initialisation des collections LAZY
        category.getTypes().size();          // Accès explicite = initialise Hibernate proxy
        category.getSubCategories().size();  // Même chose ici si besoin

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
    public Page<Category> getCategories(Pageable pageable, String search) {
        if (!StringUtils.hasText(search)) {
            return repository.findAll(pageable); // no spec needed
        }
        Specification<Category> spec = buildSearchSpec(search);
        return this.repository.findAll(spec, pageable);
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

    private Specification<Category> buildSearchSpec(String search) {
        if (!StringUtils.hasText(search)) return null; // no filter -> all

        final String like = "%" + search.toLowerCase() + "%";

        return (root, query, cb) -> cb.or(
                cb.like(cb.lower(root.get("id")), like),
                cb.like(cb.lower(root.get("title")), like),
                cb.like(cb.lower(root.get("description")), like)
        );
    }
}
