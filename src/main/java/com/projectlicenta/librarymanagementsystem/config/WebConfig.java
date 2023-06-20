package com.projectlicenta.librarymanagementsystem.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class WebConfig {
    @Value("${jokes.api.timeout}")
    private Integer restTemplateTimeout;
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder){
        return builder
                .setConnectTimeout(Duration.ofMillis(restTemplateTimeout))
                .setReadTimeout(Duration.ofMillis((restTemplateTimeout)))
                .build();
    }
}
