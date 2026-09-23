package com.payment.Client;

import com.payment.Dto.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

@Component
@Configuration
@RequiredArgsConstructor
public class OrderClient {

    private final RestClient restTemplate;


    public OrderResponse getOrderById(Long id)
    {

        return restTemplate.get().uri("/{id}",id).retrieve().body(OrderResponse.class);
    }

}
