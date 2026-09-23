package com.order.order_service.Entities;

import com.order.order_service.Enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

     @Column(nullable = false,unique = true)
     private String orderNumber;

     @Column(nullable = false)
     private Long customerId;

     @Enumerated(EnumType.STRING)
     @Column(nullable = false)
     private OrderStatus status=OrderStatus.CREATED;

     @Column(nullable = false)
     private BigDecimal amount;

     @OneToMany(mappedBy = "order",cascade = CascadeType.ALL,orphanRemoval = true)
     private List<OrderItem> orderItems=new ArrayList<>();

     @CreationTimestamp
     @Column(nullable = false, updatable = false)
     private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Version
    private Long version;




}
