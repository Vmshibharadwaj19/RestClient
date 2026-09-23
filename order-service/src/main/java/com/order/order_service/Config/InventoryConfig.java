package com.order.order_service.Config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

@Configuration
public class InventoryConfig {

    @Bean
    public RestClient inventoryconFig(@Value("${inventory.service.url}") String url) {

        return RestClient.builder().baseUrl(url).build();
    }
}
