package com.order.order_service.Client;


import com.order.order_service.Dto.ProductResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class ProductClient {

    private final RestClient restClient;

    public void validateProductId(Long productId) {

        try {
            restClient.get().uri("/productId/{id}", productId).retrieve().toBodilessEntity();
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("No resource found");
        }
    }
    public ProductResponseDto getProductById(Long id)
    {
        try {
         return   restClient.get().uri("/productId/{id}", id).retrieve().body(ProductResponseDto.class);
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("No resource found");
        }

    }
}
