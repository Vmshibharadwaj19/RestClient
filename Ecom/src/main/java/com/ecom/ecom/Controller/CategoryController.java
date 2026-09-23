package com.ecom.ecom.Controller;

import com.ecom.ecom.Dto.CategoryCreateRequest;
import com.ecom.ecom.Dto.CategoryResponseDto;
import com.ecom.ecom.Dto.CategoryUpdateRequest;
import com.ecom.ecom.Exception.ApiError;
import com.ecom.ecom.Service.CategorysService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
@Tag(
        name = "Category Controller",
        description = "Controller endpoints for Category functionalities"
)
public class CategoryController {

    private final CategorysService categoryService;


    // ----------------------------------------------------
    // CREATE
    // ----------------------------------------------------

    @Operation(
            summary = "Create category",
            description = "Creates a new product category"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Category created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = CategoryResponseDto.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ApiError.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Category name or slug already exists",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ApiError.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ApiError.class
                            )
                    )
            )
    })
    @PostMapping("/create")
    public ResponseEntity<CategoryResponseDto> createCategory(
            @Valid @RequestBody CategoryCreateRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(categoryService.createCategory(request));
    }


    // ----------------------------------------------------
    // UPDATE
    // ----------------------------------------------------

    @Operation(
            summary = "Update category",
            description = "Partially updates an existing category"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Category updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = CategoryResponseDto.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ApiError.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Category not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ApiError.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Category name or slug already exists",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ApiError.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ApiError.class
                            )
                    )
            )
    })
    @PatchMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryUpdateRequest request) {

        return ResponseEntity.ok(
                categoryService.updateCategory(id, request)
        );
    }


    // ----------------------------------------------------
    // GET ALL
    // ----------------------------------------------------

    @Operation(
            summary = "Get all categories",
            description = "Fetches categories using pagination and sorting"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Categories fetched successfully"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ApiError.class
                            )
                    )
            )
    })
    @GetMapping
    public ResponseEntity<Page<CategoryResponseDto>> getAllCategories(
            @ParameterObject Pageable pageable) {

        return ResponseEntity.ok(
                categoryService.getAllCategories(pageable)
        );
    }


    // ----------------------------------------------------
    // GET BY SLUG
    // ----------------------------------------------------

    @Operation(
            summary = "Get category by slug",
            description = "Fetches a category using its URL-friendly slug"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Category found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = CategoryResponseDto.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Category not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ApiError.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ApiError.class
                            )
                    )
            )
    })
    @GetMapping("/byslug")
    public ResponseEntity<CategoryResponseDto> getCategoryBySlug(
            @RequestParam String slug) {

        return ResponseEntity.ok(
                categoryService.getCategoryBySlug(slug)
        );
    }


    // ----------------------------------------------------
    // CHANGE STATUS
    // ----------------------------------------------------

    @Operation(
            summary = "Change category status",
            description = "Activates or deactivates an existing category"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Category status updated successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid active status",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ApiError.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Category not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ApiError.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ApiError.class
                            )
                    )
            )
    })
    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> changeCategoryStatus(
            @PathVariable Long id,
            @RequestParam Boolean active) {

        categoryService.changeCategoryStatus(id, active);

        return ResponseEntity.noContent().build();
    }


    // ----------------------------------------------------
    // DELETE
    // ----------------------------------------------------

    @Operation(
            summary = "Delete category",
            description = "Deletes a category if it is not associated with any products"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Category deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Category not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ApiError.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Category is associated with products",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ApiError.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ApiError.class
                            )
                    )
            )
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCategory(
            @PathVariable Long id) {

        categoryService.deleteCategory(id);

        return ResponseEntity.noContent().build();
    }
}