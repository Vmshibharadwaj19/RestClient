package com.ecom.ecom.Dto;


import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Brand response")
public record BrandResponseDto(

        @Schema(
                description = "Unique identifier of the brand",
                example = "1"
        )
        Long id,

        @Schema(
                description = "Brand name",
                example = "Samsung"
        )
        String brandName,

        @Schema(
                description = "URL-friendly identifier of the brand",
                example = "samsung"
        )
        String slug,

        @Schema(
                description = "Brand logo URL",
                example = "https://cdn.example.com/brands/samsung.png"
        )
        String logoUrl,

        @Schema(
                description = "Official website of the brand",
                example = "https://www.samsung.com"
        )
        String websiteUrl,

        @Schema(
                description = "Indicates whether the brand is active",
                example = "true"
        )
        Boolean active,

        @Schema(
                description = "Date and time when the brand was created",
                example = "2026-09-21T10:30:00"
        )
        LocalDateTime createdAt,

        @Schema(
                description = "Date and time when the brand was last updated",
                example = "2026-09-21T11:45:00"
        )
        LocalDateTime updatedAt

) {
}
