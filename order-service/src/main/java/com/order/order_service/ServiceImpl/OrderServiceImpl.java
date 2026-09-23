package com.order.order_service.ServiceImpl;

import com.order.order_service.Client.InventoryClient;
import com.order.order_service.Client.PaymentConfig;
import com.order.order_service.Client.ProductClient;
import com.order.order_service.Dto.*;
import com.order.order_service.Entities.Order;
import com.order.order_service.Entities.OrderItem;
import com.order.order_service.Enums.PaymentStatus;
import com.order.order_service.Repo.OrderItemRepo;
import com.order.order_service.Repo.OrderRepo;
import com.order.order_service.ResourceNotFoundException;
import com.order.order_service.Service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final ProductClient productClient;
    private final OrderItemRepo oirepo;
    private final OrderRepo repo;
    public final InventoryClient inv;
    private final PaymentConfig pc;
    @Override
    @Transactional
    public OrderResponse createOrder(OrderCreateRequest request) {
        log.debug("Create Order Request");
        Order o=new Order();
        List<OrderItem> oi=new ArrayList<>();

       try {

           BigDecimal tot = BigDecimal.ZERO;
           for (OrderItemRequest item : request.getItems()) {
               OrderItem orderItem = new OrderItem();

               ProductResponseDto p = findProductById(item.getProductId());
               inv.reserveStock(p.id(), item.getQuantity());

               orderItem.setProductId(p.id());

               orderItem.setQuantity(item.getQuantity());
               oi.add(orderItem);
               orderItem.setProductName(p.name());
               orderItem.setUnitPrice(p.price());
               BigDecimal b = p.price().multiply(BigDecimal.valueOf(item.getQuantity()));
               orderItem.setLineTotal(b);
               o.getOrderItems().add(orderItem);
               orderItem.setOrder(o);
               tot = tot.add(orderItem.getLineTotal());


           }
           o.setCustomerId(request.getCustomerId());
           o.setAmount(tot);
           o.setOrderNumber("ordernumber-" + request.getCustomerId());

           Order a = repo.saveAndFlush(o);

           PaymentRequest p=new PaymentRequest(a.getId(),a.getAmount(),request.getPaymentMethod());
          PaymentResponse ps=  pc.processPayment(p);
          if(ps.paymentStatus()== PaymentStatus.FAILED){
              throw new RuntimeException("Payment Failed");
          }
           for (OrderItem item : oi) {
               inv.confirmStock(
                       item.getProductId(),
                       item.getQuantity()
               );
           }

           log.info("order created successfully with id {}", a.getId());
       }catch (Exception e){

           for(OrderItem i : oi)
           {

               try {
                     inv.releaseStock(i.getProductId(),i.getQuantity());

                   }
                   catch(Exception ex)
                   {
                      log.error("failed to release lock "+ i.getId());
                   }
               }

           throw  e;

           }

       return mapper(o);
    }









    @Override
    public OrderResponse getOrderById(Long orderId) {
        Order a=repo.findById(orderId).orElseThrow(
                ()->new RuntimeException("no order found with id")
        );
        return mapper(a);
    }

    private ProductResponseDto findProductById(Long productId) {

       return productClient.getProductById(productId);

    }
    private OrderResponse mapper(Order order) {

        List<OrderItemResponse> items = order.getOrderItems()
                .stream()
                .map(item -> new OrderItemResponse(
                        item.getId(),
                        item.getProductId(),
                        item.getProductName(),
                        item.getUnitPrice(),
                        item.getQuantity(),
                        item.getLineTotal()
                ))
                .toList();

        return new OrderResponse(
                order.getId(),
                order.getOrderNumber(),
                order.getCustomerId(),
                order.getStatus(),
                order.getAmount(),
                items,
                order.getVersion(),
                order.getCreatedAt(),
                order.getUpdatedAt()
        );
    }


}
