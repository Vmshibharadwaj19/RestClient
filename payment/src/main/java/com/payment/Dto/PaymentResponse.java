package com.payment.Dto;

import com.payment.Enums.PaymentMethod;
import com.payment.Enums.PaymentStatus;

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