package com.miniproject.category.controller;



import com.miniproject.category.dto.CategoryRequestDto;
import com.miniproject.category.dto.CategoryResponseDto;
import com.miniproject.category.dto.CategoryUpdateRequest;
import com.miniproject.category.exception.ApiError;
import com.miniproject.category.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@Tag(
        name="Category",
        description = "Api s for create,update,delete and select"
)
public class CategoryController {

    private final CategoryService categoryService;

    // CREATE
    @Operation( summary = "Create Category Record",

        description = "a record should be created when an api is called"

    )
    @ApiResponses(
            {
                    @ApiResponse(
                            responseCode = "201",

                    description = "Travel plan created successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = CategoryResponseDto.class
                                    )
                            )
                    ),

                    @ApiResponse(
                            responseCode = "400",
                            description = "Failed to create a record"

                            ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Category Not Found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ApiError.class
                                    )

                            )


                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "internal server error",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ApiError.class
                                    )

                            )

                    )
            }
    )
    @PostMapping
    public ResponseEntity<CategoryResponseDto> createCategory(
            @Valid @RequestBody CategoryRequestDto request) {

        CategoryResponseDto response =
                categoryService.create(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // UPDATE
    @Operation(
            summary = "Updating Record of Category plan",
            description = """
                    Updation of multiple fields or singular fields of Category
                    """

    )
    @ApiResponses(
            {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Travel plan updated sucessfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema =  @Schema(
                                            implementation = CategoryResponseDto.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "failed to update "
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No resource with id",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation =  ApiError.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "internal server error"
                    )



            }
    )
    @PatchMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> updateCategory(
          @Parameter(
                  example = "10",
                  description = "Category id"

          ) @PathVariable Long id,
            @ParameterObject @Valid @RequestBody CategoryUpdateRequest request) {

        CategoryResponseDto response =
                categoryService.update(id, request);

        return ResponseEntity.ok(response);
    }
@Operation(
        summary = """
                Find the record using Id
                
                """,description = """
            description for later
        """
)
@ApiResponses(
        {
                @ApiResponse(
                        responseCode = "200",
                        description = "Fetch successful",
                        content = @Content(
                                mediaType = "application/json",
                                schema =  @Schema(
                                        implementation =  CategoryResponseDto.class
                                )
                        )
                ),
                @ApiResponse(
                        responseCode = "404",
                        description = "No resource found",
                        content = @Content(
                                mediaType = "application/json",
                                schema =  @Schema(
                                        implementation =  ApiError.class
                                )
                        )
                ),
                @ApiResponse(
                        responseCode = "500",
                        description = "internal server error",
                        content = @Content(
                                mediaType = "application/json",
                                schema =  @Schema(
                                        implementation =  ApiError.class
                                )
                        )
                )
        }
)
    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> findById(
         @Parameter(
                 example = "1",
                 description = "Category id"
         )   @PathVariable Long id) {

        return ResponseEntity.ok(
                categoryService.findById(id)
        );
    }

    // GET ALL WITH PAGINATION
    @GetMapping
    public ResponseEntity<Page<CategoryResponseDto>> findAll(
            Pageable pageable) {

        return ResponseEntity.ok(
                categoryService.findAll(pageable)
        );
    }

    // HARD DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(
            @PathVariable Long id) {

        categoryService.deleteCategory(id);

        return ResponseEntity.noContent().build();
    }

    // CHANGE ACTIVE / INACTIVE STATUS
    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> changeCategoryStatus(
            @PathVariable Long id) {

        categoryService.changeCategoryStatus(id);

        return ResponseEntity.noContent().build();
    }
}