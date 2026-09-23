package com.ecom.ecom.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Request payload for creating a category")
public class CategoryCreateRequest {

    @NotBlank(message = "Category name cannot be blank")
    @Size(
            min = 3,
            max = 100,
            message = "Category name must be between 3 and 100 characters"
    )
    @Schema(
            description = "Name of the product category",
            example = "Mobile Phones"
    )
    private String categoryName;

    @NotBlank(message = "Slug cannot be blank")
    @Schema(
            description = "URL-friendly category identifier",
            example = "mobile-phones"
    )
    private String slug;

    @Schema(
            description = "Description of the category",
            example = "Smartphones and mobile devices"
    )
    private String description;

    @Schema(
            description = "Category image URL",
            example = "https://cdn.example.com/categories/mobile-phones.png"
    )
    private String imageUrl;

    @Schema(
            description = "Indicates whether the category is active",
            example = "true"
    )
    private Boolean active = true;

    public Boolean getActive()
    {
        return active;
    }
}