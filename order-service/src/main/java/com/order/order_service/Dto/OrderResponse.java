package com.order.order_service.Dto;

import com.order.order_service.Enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
        Long id,
        String orderNumber,
        Long customerId,
        OrderStatus status,
        BigDecimal totalAmount,
        List<OrderItemResponse> items,
        Long version,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}