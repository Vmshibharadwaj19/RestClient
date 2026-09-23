package com.miniproject.category.dto;

import com.miniproject.category.ActiveStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Schema(description = "travel response")
public record TravelResponseDto(
        Long id,
        String name,
        String description,
        BigInteger budget,
        ActiveStatus status,
        LocalDateTime created,
        LocalDateTime updated
) {
}
