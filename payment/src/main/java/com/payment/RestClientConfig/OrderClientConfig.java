package com.payment.RestClientConfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class OrderClientConfig {


    @Bean
    public RestClient restClient(@Value("${order.service.url}") String url) {

        return RestClient.builder().baseUrl(url).build();


    }
}
