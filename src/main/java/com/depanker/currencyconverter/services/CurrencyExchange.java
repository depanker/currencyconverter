package com.depanker.currencyconverter.services;

import com.depanker.currencyconverter.bean.ConversionRequest;
import com.depanker.currencyconverter.bean.ExchangeRatesData;
import reactor.core.publisher.Mono;

public interface CurrencyExchange {
    Mono<ExchangeRatesData> getConversionAmount(ConversionRequest conversionRequest);
}
