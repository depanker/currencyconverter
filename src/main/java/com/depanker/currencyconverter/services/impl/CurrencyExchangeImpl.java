package com.depanker.currencyconverter.services.impl;

import com.depanker.currencyconverter.bean.AlternateExchangeRates;
import com.depanker.currencyconverter.bean.ConversionRequest;
import com.depanker.currencyconverter.bean.ExchangeRates;
import com.depanker.currencyconverter.bean.ExchangeRatesData;
import com.depanker.currencyconverter.services.CurrencyExchange;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@Slf4j
//@RequiredArgsConstructor
public class CurrencyExchangeImpl implements CurrencyExchange {


    private static final String PRIMARY_RESOURCE = "/latest?base={FROM}";
    private static final String ALTERNATE_RESOURCE = "/{FROM}";
    @Autowired
    private WebClient webClient;
    @Autowired
    @Qualifier("alternativeWebClient")
    private WebClient alternativeWebClient;

    @Override
    public Mono<ExchangeRatesData> getConversionAmount(ConversionRequest conversionRequest) {
        return webClient.get().uri(PRIMARY_RESOURCE, conversionRequest.getFrom())
                .retrieve()
                .bodyToMono(ExchangeRates.class)
                .log()
                .filter(exchangeRates -> exchangeRates.getRates().containsKey(conversionRequest.getTo()))
                .map(exchangeRates -> new ExchangeRatesData(exchangeRates.getRates().get(conversionRequest.getTo())))
                .onErrorResume(throwable -> getAlternativeFlow(conversionRequest))
                .subscribeOn(Schedulers.boundedElastic())
                ;

    }

    private Mono<? extends ExchangeRatesData> getAlternativeFlow(ConversionRequest conversionRequest) {
        return alternativeWebClient.get()
                .uri(uriBuilder -> uriBuilder.path(ALTERNATE_RESOURCE).build(conversionRequest.getFrom()))
                .retrieve()
                .bodyToMono(AlternateExchangeRates.class)
                .filter(exchangeRates -> exchangeRates.getRates().containsKey(conversionRequest.getTo()))
                .map(alternateExchangeRates ->
                        new ExchangeRatesData(alternateExchangeRates.getRates().get(conversionRequest.getTo())))
                .subscribeOn(Schedulers.boundedElastic());
    }
}
