package com.order.order_service.Config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class Payment {

    @Bean
    public RestClient paymentClient(@Value("${payment.service.url}")String url)
    {
        return  RestClient.builder().baseUrl(url).build();
    }
}
