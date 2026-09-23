package com.order.order_service.Client;

import com.order.order_service.Dto.PaymentRequest;
import com.order.order_service.Dto.PaymentResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class PaymentConfig {

    private final RestClient restClient;

    public PaymentConfig(
            @Qualifier("paymentClient") RestClient restClient) {
        this.restClient = restClient;
    }

    public PaymentResponse processPayment(PaymentRequest paymentRequest) {

        return restClient.post()
                .uri("/api/payments")
                .body(paymentRequest)
                .retrieve()
                .body(PaymentResponse.class);
    }
}