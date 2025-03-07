package org.mts.announcesservice.repositories;

import org.mts.announcesservice.entities.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, String> {

    public Page<Category> findAllByTitleContains(String title, Pageable pageable);

   public boolean existsCategoryByTitle(String title);

    boolean existsCategoryByTitleEqualsIgnoreCase(String title);
}
