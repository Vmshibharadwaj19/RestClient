package com.Ecom.invertory_service.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestConfig {

    @Bean
    public RestClient restTemplate( @Value("${product.service.url}") String baseUrl) {

        return RestClient.builder().baseUrl(baseUrl).build();
    }
}
