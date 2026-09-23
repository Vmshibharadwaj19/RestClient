package com.order.order_service.Dto;



import com.order.order_service.Enums.PaymentMethod;
import com.order.order_service.Enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentResponse(
        Long id,
        Long orderId,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        PaymentStatus paymentStatus,
        String transactionId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}