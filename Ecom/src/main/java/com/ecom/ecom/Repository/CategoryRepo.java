package com.ecom.ecom.Repository;

import com.ecom.ecom.Entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface CategoryRepo extends JpaRepository<Category,Long> {

    public boolean existsByCategoryNameIgnoreCase(String name);
    public boolean existsBySlug(String slug);
    public Optional<Category> findBySlug(String slug);

}
