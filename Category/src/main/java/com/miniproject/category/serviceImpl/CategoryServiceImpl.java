package com.miniproject.category.serviceImpl;

import com.miniproject.category.ActiveStatus;
import com.miniproject.category.dto.CategoryRequestDto;
import com.miniproject.category.dto.CategoryResponseDto;
import com.miniproject.category.dto.CategoryUpdateRequest;
import com.miniproject.category.entitites.Category;
import com.miniproject.category.exception.NoResourceFoundException;
import com.miniproject.category.repository.CategoryRepository;
import com.miniproject.category.service.CategoryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public CategoryResponseDto create(CategoryRequestDto request) {

        log.debug(
                "Creating category with categoryName={}",
                request.getCategoryName()
        );

        Category category = new Category();

        category.setCategoryName(request.getCategoryName());
        category.setActiveSw(request.getActiveSw());

        Category savedCategory =
                categoryRepository.save(category);

        log.info(
                "Category created successfully categoryId={}",
                savedCategory.getId()
        );

        return mapper(savedCategory);
    }

    @Override
    @Transactional
    public CategoryResponseDto update(
            Long id,
            CategoryUpdateRequest request) {

        log.debug("Updating category categoryId={}", id);

        Category category = categoryRepository
                .findById(id)
                .orElseThrow(() -> {

                    log.warn(
                            "Category update failed. Category not found categoryId={}",
                            id
                    );

                    return new NoResourceFoundException(
                            "No Category found with id: " + id
                    );
                });

        if (request.getCategoryName() != null) {
            category.setCategoryName(
                    request.getCategoryName().trim()
            );
        }

        if (request.getActiveSw() != null) {
            category.setActiveSw(
                    request.getActiveSw()
            );
        }

        Category updatedCategory =
                categoryRepository.save(category);

        log.info(
                "Category updated successfully categoryId={}",
                updatedCategory.getId()
        );

        return mapper(updatedCategory);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryResponseDto findById(Long id) {

        log.debug("Fetching category categoryId={}", id);

        Category category = categoryRepository
                .findById(id)
                .orElseThrow(() ->
                        new NoResourceFoundException(
                                "No Category found with id: " + id
                        )
                );

        return mapper(category);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CategoryResponseDto> findAll(Pageable pageable) {

        log.debug(
                "Fetching categories page={}, size={}",
                pageable.getPageNumber(),
                pageable.getPageSize()
        );

        return categoryRepository
                .findAll(pageable)
                .map(this::mapper);
    }

    @Override
    @Transactional
    public boolean deleteCategory(Long id) {

        log.debug("Deleting category categoryId={}", id);

        Category category = categoryRepository
                .findById(id)
                .orElseThrow(() -> {

                    log.warn(
                            "Category deletion failed. Category not found categoryId={}",
                            id
                    );

                    return new NoResourceFoundException(
                            "No Category found with id: " + id
                    );
                });

        categoryRepository.delete(category);

        log.info(
                "Category deleted successfully categoryId={}",
                id
        );

        return true;
    }

    @Override
    @Transactional
    public boolean changeCategoryStatus(Long id) {

        log.debug(
                "Changing category status categoryId={}",
                id
        );

        Category category = categoryRepository
                .findById(id)
                .orElseThrow(() -> {

                    log.warn(
                            "Category status change failed. Category not found categoryId={}",
                            id
                    );

                    return new NoResourceFoundException(
                            "No Category found with id: " + id
                    );
                });

        ActiveStatus oldStatus =
                category.getActiveSw();

        if (oldStatus == ActiveStatus.Active) {

            category.setActiveSw(
                    ActiveStatus.Inactive
            );

        } else {

            category.setActiveSw(
                    ActiveStatus.Active
            );
        }

        categoryRepository.save(category);

        log.info(
                "Category status changed categoryId={}, oldStatus={}, newStatus={}",
                id,
                oldStatus,
                category.getActiveSw()
        );

        return true;
    }

    private CategoryResponseDto mapper(Category category) {

        return new CategoryResponseDto(
                category.getId(),
                category.getCategoryName(),
                category.getActiveSw(),
                category.getCreateDate(),
                category.getUpdateDate()
        );
    }
}