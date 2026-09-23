package com.ecom.ecom.ServiceImpl;

import com.ecom.ecom.Dto.CategoryCreateRequest;
import com.ecom.ecom.Dto.CategoryResponseDto;
import com.ecom.ecom.Dto.CategoryUpdateRequest;
import com.ecom.ecom.Entities.Category;
import com.ecom.ecom.Exception.CategoryInUseException;
import com.ecom.ecom.Exception.DuplicateCategoryNameException;
import com.ecom.ecom.Exception.DuplicateSlugException;
import com.ecom.ecom.Exception.ResourceNotFoundException;
import com.ecom.ecom.Repository.CategoryRepo;
import com.ecom.ecom.Repository.ProductRepo;
import com.ecom.ecom.Service.CategorysService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategorysService {

    private final CategoryRepo categoryRepo;
    private final ProductRepo productRepo;


    // ----------------------------------------------------
    // CREATE CATEGORY
    // ----------------------------------------------------

    @Override
    @Transactional
    public CategoryResponseDto createCategory(
            CategoryCreateRequest request) {

        log.debug(
                "Creating category categoryName={}, slug={}",
                request.getCategoryName(),
                request.getSlug()
        );

        if (categoryRepo.existsByCategoryNameIgnoreCase(
                request.getCategoryName())) {

            log.warn(
                    "Category already exists categoryName={}",
                    request.getCategoryName()
            );

            throw new DuplicateCategoryNameException(
                    "Category already exists with name: "
                            + request.getCategoryName()
            );
        }

        if (categoryRepo.existsBySlug(request.getSlug())) {

            log.warn(
                    "Category slug already exists slug={}",
                    request.getSlug()
            );

            throw new DuplicateSlugException(
                    "Slug already exists: "
                            + request.getSlug()
            );
        }

        Category category = new Category();

        category.setCategoryName(
                request.getCategoryName()
        );

        category.setSlug(
                request.getSlug()
        );

        category.setDescription(
                request.getDescription()
        );

        category.setImageUrl(
                request.getImageUrl()
        );

        /*
         * If your DTO:
         *
         * private Boolean active = true;
         *
         * then omitted → true
         * true → true
         * false → false
         */
        category.setActive(
                request.getActive()
        );

        Category savedCategory =
                categoryRepo.save(category);

        log.info(
                "Category created successfully categoryId={}",
                savedCategory.getId()
        );

        return mapper(savedCategory);
    }


    // ----------------------------------------------------
    // UPDATE CATEGORY
    // ----------------------------------------------------

    @Override
    @Transactional
    public CategoryResponseDto updateCategory(
            Long id,
            CategoryUpdateRequest request) {

        log.debug(
                "Updating category categoryId={}",
                id
        );

        Category category =
                categoryRepo.findById(id)
                        .orElseThrow(() -> {

                            log.warn(
                                    "Category not found categoryId={}",
                                    id
                            );

                            return new ResourceNotFoundException(
                                    "No category exists with id: " + id
                            );
                        });


        // CATEGORY NAME
        if (request.getCategoryName() != null) {

            /*
             * If user sends existing value of same record:
             *
             * Samsung → Samsung
             *
             * don't perform duplicate rejection.
             */

            if (!category.getCategoryName()
                    .equalsIgnoreCase(
                            request.getCategoryName())) {

                boolean exists =
                        categoryRepo
                                .existsByCategoryNameIgnoreCase(
                                        request.getCategoryName()
                                );

                if (exists) {

                    log.warn(
                            "Category name already exists categoryName={}",
                            request.getCategoryName()
                    );

                    throw new DuplicateCategoryNameException(
                            "Category already exists with name: "
                                    + request.getCategoryName()
                    );
                }

                category.setCategoryName(
                        request.getCategoryName()
                );
            }
        }


        // SLUG
        if (request.getSlug() != null) {

            if (!category.getSlug()
                    .equalsIgnoreCase(
                            request.getSlug())) {

                boolean exists =
                        categoryRepo.existsBySlug(
                                request.getSlug()
                        );

                if (exists) {

                    log.warn(
                            "Category slug already exists slug={}",
                            request.getSlug()
                    );

                    throw new DuplicateSlugException(
                            "Slug already exists: "
                                    + request.getSlug()
                    );
                }

                category.setSlug(
                        request.getSlug()
                );
            }
        }


        // DESCRIPTION
        if (request.getDescription() != null) {

            category.setDescription(
                    request.getDescription()
            );
        }


        // IMAGE
        if (request.getImageUrl() != null) {

            category.setImageUrl(
                    request.getImageUrl()
            );
        }


        // ACTIVE STATUS
        if (request.getActive() != null) {

            category.setActive(
                    request.getActive()
            );
        }


        Category savedCategory =
                categoryRepo.save(category);

        log.info(
                "Category updated successfully categoryId={}",
                savedCategory.getId()
        );

        return mapper(savedCategory);
    }


    // ----------------------------------------------------
    // GET ALL
    // ----------------------------------------------------

    @Override
    @Transactional(readOnly = true)
    public Page<CategoryResponseDto> getAllCategories(
            Pageable pageable) {

        log.debug(
                "Fetching categories page={}, size={}",
                pageable.getPageNumber(),
                pageable.getPageSize()
        );

        Page<Category> categories =
                categoryRepo.findAll(pageable);

        return categories.map(this::mapper);
    }


    // ----------------------------------------------------
    // GET BY SLUG
    // ----------------------------------------------------

    @Override
    @Transactional(readOnly = true)
    public CategoryResponseDto getCategoryBySlug(
            String slug) {

        log.debug(
                "Fetching category by slug={}",
                slug
        );

        Category category =
                categoryRepo.findBySlug(slug)
                        .orElseThrow(() -> {

                            log.warn(
                                    "Category not found slug={}",
                                    slug
                            );

                            return new ResourceNotFoundException(
                                    "No category exists with slug: "
                                            + slug
                            );
                        });

        return mapper(category);
    }


    // ----------------------------------------------------
    // CHANGE STATUS
    // ----------------------------------------------------

    @Override
    @Transactional
    public void changeCategoryStatus(
            Long id,
            Boolean active) {

        log.debug(
                "Changing category status categoryId={}, active={}",
                id,
                active
        );

        if (active == null) {
            throw new IllegalArgumentException(
                    "Active status cannot be null"
            );
        }

        Category category =
                categoryRepo.findById(id)
                        .orElseThrow(() -> {

                            log.warn(
                                    "Category not found categoryId={}",
                                    id
                            );

                            return new ResourceNotFoundException(
                                    "No category exists with id: " + id
                            );
                        });

        category.setActive(active);

        categoryRepo.save(category);

        log.info(
                "Category status changed successfully categoryId={}, active={}",
                id,
                active
        );
    }


    // ----------------------------------------------------
    // DELETE CATEGORY
    // ----------------------------------------------------

    @Override
    @Transactional
    public void deleteCategory(Long id) {

        log.debug(
                "Deleting category categoryId={}",
                id
        );

        Category category =
                categoryRepo.findById(id)
                        .orElseThrow(() -> {

                            log.warn(
                                    "Category not found categoryId={}",
                                    id
                            );

                            return new ResourceNotFoundException(
                                    "No category exists with id: " + id
                            );
                        });


        /*
         * Don't delete Category if Products
         * are already referencing it.
         */
        boolean categoryInUse =
                productRepo.existsByCategory_Id(id);

        if (categoryInUse) {

            log.warn(
                    "Cannot delete category because products are associated categoryId={}",
                    id
            );

            throw new CategoryInUseException(
                    "Category cannot be deleted because products are associated with it"
            );
        }


        categoryRepo.delete(category);

        log.info(
                "Category deleted successfully categoryId={}",
                id
        );
    }


    // ----------------------------------------------------
    // ENTITY → RESPONSE DTO
    // ----------------------------------------------------

    private CategoryResponseDto mapper(
            Category category) {

        return new CategoryResponseDto(
                category.getId(),
                category.getCategoryName(),
                category.getSlug(),
                category.getDescription(),
                category.getImageUrl(),
                category.getActive(),
                category.getCreatedAt(),
                category.getUpdatedAt()
        );
    }
}