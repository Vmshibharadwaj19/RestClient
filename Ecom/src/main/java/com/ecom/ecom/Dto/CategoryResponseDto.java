package com.ecom.ecom.Dto;


import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Category response")
public record CategoryResponseDto(

        @Schema(
                description = "Unique category identifier",
                example = "5"
        )
        Long id,

        @Schema(
                description = "Category name",
                example = "Mobile Phones"
        )
        String categoryName,

        @Schema(
                description = "URL-friendly category identifier",
                example = "mobile-phones"
        )
        String slug,

        @Schema(
                description = "Category description",
                example = "Smartphones and mobile devices"
        )
        String description,

        @Schema(
                description = "Category image URL",
                example = "https://cdn.example.com/categories/mobile-phones.png"
        )
        String imageUrl,

        @Schema(
                description = "Whether the category is active",
                example = "true"
        )
        Boolean active,

        @Schema(
                description = "Category creation timestamp",
                example = "2026-09-21T10:30:00"
        )
        LocalDateTime createdAt,

        @Schema(
                description = "Category last-update timestamp",
                example = "2026-09-21T12:45:00"
        )
        LocalDateTime updatedAt

) {
}