package com.order.order_service.Service;

import com.order.order_service.Dto.OrderCreateRequest;
import com.order.order_service.Dto.OrderResponse;

public interface OrderService {

    OrderResponse createOrder(OrderCreateRequest request);

    OrderResponse getOrderById(Long orderId);
}