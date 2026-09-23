package com.Ecom.invertory_service.Client;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class ProductRestClient {
    private final RestClient restTemplate;


    public void validateProductIdExists(Long id) {
        try {
            restTemplate.get().uri("/productId/{id}", id).retrieve().toBodilessEntity();
        }catch (HttpClientErrorException e) {
            throw new RuntimeException("No resource found");
        }
    }

}
