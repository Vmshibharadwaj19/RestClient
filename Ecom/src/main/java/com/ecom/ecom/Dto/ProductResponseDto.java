package com.ecom.ecom.Dto;

import com.ecom.ecom.ProductStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductResponseDto(
        Long id,
        String sku,
        String name,
        String description,
        BigDecimal price,
        BigDecimal discountPercentage,
        BigDecimal rating,
        Integer reviewCount,
        ProductStatus status,
        Long categoryId,
        String categoryName,
        Long brandId,
        String brandName,
        String thumbnailUrl,
        Boolean featured,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Long version
) {}