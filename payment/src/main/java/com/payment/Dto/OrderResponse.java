package com.payment.Dto;




import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
        Long id,
        String orderNumber,
        Long customerId,
        String status,
        BigDecimal totalAmount,
        Long version,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}