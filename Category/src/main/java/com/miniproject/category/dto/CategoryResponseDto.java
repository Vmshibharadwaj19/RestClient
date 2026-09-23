package com.miniproject.category.dto;

import com.miniproject.category.ActiveStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record CategoryResponseDto(
        @Schema(example = "10")
        Long id,
        @Schema(example = "Adventure")
        String categoryName,
        @Schema(example = "Active")
        ActiveStatus status,
        @Schema(example = "2026-12-18T11:32:12")
        LocalDateTime createdDate,
        @Schema(example = "2026-09-21T10:30:00")
        LocalDateTime updateDate


) {
}
