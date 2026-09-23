package com.order.order_service.Repo;

import com.order.order_service.Entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepo extends JpaRepository<OrderItem, Long> {
}
