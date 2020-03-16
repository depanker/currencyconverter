package com.depanker.currencyconverter.services.impl;

import com.depanker.currencyconverter.bean.ConversionRequest;
import com.depanker.currencyconverter.bean.ConversionResponse;
import com.depanker.currencyconverter.services.CurrencyConverter;
import com.depanker.currencyconverter.services.CurrencyExchange;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CurrencyConverterImpl implements CurrencyConverter {
    private final CurrencyExchange currencyExchangeImpl;

    @Override
    public Mono<ConversionResponse> getConversionResponse(ConversionRequest request) {
        return currencyExchangeImpl.getConversionAmount(request)
                .map(exchangeRatesData -> exchangeRatesData.getRate())
                .map(rate -> {
                    ConversionResponse conversionResponse = new ConversionResponse();
                    BeanUtils.copyProperties(request, conversionResponse);
                    conversionResponse.setConverted(request.getAmount() * rate);
                    return conversionResponse;
                });
    }
}
