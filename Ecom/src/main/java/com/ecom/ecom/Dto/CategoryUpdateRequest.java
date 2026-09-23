package com.ecom.ecom.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Request payload for partially updating a category")
public class CategoryUpdateRequest {

    @Size(
            min = 3,
            max = 100,
            message = "Category name must be between 3 and 100 characters"
    )
    @Schema(
            description = "Updated category name",
            example = "Smartphones"
    )
    private String categoryName;

    @Pattern(
            regexp = "^[a-z0-9]+(?:-[a-z0-9]+)*$",
            message = "Slug must contain lowercase letters, numbers and hyphens only"
    )
    @Schema(
            description = "Updated URL-friendly category identifier",
            example = "smartphones"
    )
    private String slug;

    @Schema(
            description = "Updated category description",
            example = "Latest smartphones and mobile devices"
    )
    private String description;

    @Schema(
            description = "Updated category image URL",
            example = "https://cdn.example.com/categories/smartphones.png"
    )
    private String imageUrl;

    @Schema(
            description = "Updated category status",
            example = "true"
    )
    private Boolean active;
}