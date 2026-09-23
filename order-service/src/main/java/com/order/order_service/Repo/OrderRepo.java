package com.order.order_service.Repo;

import com.order.order_service.Entities.Order;
import com.order.order_service.Entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order, Long> {
}
