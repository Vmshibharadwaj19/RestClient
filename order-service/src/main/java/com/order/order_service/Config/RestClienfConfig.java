package com.order.order_service.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClienfConfig {

    @Bean
    public RestClient restClient(@Value("${product.service.url}") String baseurl) {

            return RestClient.builder().baseUrl(baseurl).build();
    }
}
