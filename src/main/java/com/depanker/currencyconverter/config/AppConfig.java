package com.depanker.currencyconverter.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Configuration
@Slf4j
public class AppConfig {
//    @Value("${application.webclient:https://api.exchangeratesapi.io,https://api.exchangerate-api.com/v4/latest}")
//    List<String> services;

    @Bean("webClient")
    @Primary
    public WebClient webClient() {
        return WebClient.create("https://api.exchangeratesapi.io");
    }
    @Bean("alternativeWebClient")
    public WebClient alternativeWebClient() {
        return WebClient.create("https://api.exchangerate-api.com/v4/latest");
    }
}
