package com.miniproject.category.repository;

import com.miniproject.category.entitites.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}
