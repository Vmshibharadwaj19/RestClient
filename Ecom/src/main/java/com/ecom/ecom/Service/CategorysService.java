package com.ecom.ecom.Service;

import com.ecom.ecom.Dto.CategoryCreateRequest;
import com.ecom.ecom.Dto.CategoryResponseDto;
import com.ecom.ecom.Dto.CategoryUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategorysService {

    CategoryResponseDto createCategory(CategoryCreateRequest request);

    CategoryResponseDto updateCategory(
            Long id,
            CategoryUpdateRequest request
    );

    Page<CategoryResponseDto> getAllCategories(Pageable pageable);

    CategoryResponseDto getCategoryBySlug(String slug);

    void changeCategoryStatus(Long id, Boolean active);

    void deleteCategory(Long id);
}
