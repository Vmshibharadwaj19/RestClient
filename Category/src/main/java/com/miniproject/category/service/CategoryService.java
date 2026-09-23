package com.miniproject.category.service;

import com.miniproject.category.dto.CategoryRequestDto;
import com.miniproject.category.dto.CategoryResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



import com.miniproject.category.dto.CategoryRequestDto;
import com.miniproject.category.dto.CategoryResponseDto;
import com.miniproject.category.dto.CategoryUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {

    CategoryResponseDto create(CategoryRequestDto request);

    CategoryResponseDto update(Long id, CategoryUpdateRequest request);

    CategoryResponseDto findById(Long id);

    Page<CategoryResponseDto> findAll(Pageable pageable);

    boolean deleteCategory(Long id);

    boolean changeCategoryStatus(Long id);
}
