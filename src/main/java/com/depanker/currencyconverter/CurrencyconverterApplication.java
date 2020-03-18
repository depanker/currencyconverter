package com.depanker.currencyconverter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Hooks;

@SpringBootApplication
public class CurrencyconverterApplication {

    public static void main(String[] args) {
        Hooks.onOperatorDebug();
        SpringApplication.run(CurrencyconverterApplication.class, args);
    }

}
