package com.order.order_service.Client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component

public class InventoryClient {

    private final RestClient restClient;

    public InventoryClient(
            @Qualifier("inventoryconFig") RestClient restClient) {

        this.restClient = restClient;
    }

    public void  reserveStock(Long productId,Integer quantity){

        restClient.put()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/inventory/product/{productId}/reserve")
                        .queryParam("quantity", quantity)
                        .build(productId))
                .retrieve()
                .toBodilessEntity();    }

    public void  releaseStock(Long productId,Integer quantity){
        restClient.put()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/inventory/product/{productId}/release")
                        .queryParam("quantity", quantity)
                        .build(productId))
                .retrieve()
                .toBodilessEntity();
    }
    public void  confirmStock(Long productId,Integer quantity){
        restClient.put()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/inventory/product/{productId}/confirm")
                        .queryParam("quantity", quantity)
                        .build(productId))
                .retrieve()
                .toBodilessEntity();
    }
}
