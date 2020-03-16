package com.depanker.currencyconverter.services;

import com.depanker.currencyconverter.bean.ConversionRequest;
import com.depanker.currencyconverter.bean.ConversionResponse;
import reactor.core.publisher.Mono;

public interface CurrencyConverter {
    Mono<ConversionResponse> getConversionResponse(ConversionRequest request);
}
