package com.depanker.currencyconverter.controller;

import com.depanker.currencyconverter.bean.ConversionRequest;
import com.depanker.currencyconverter.bean.ConversionResponse;
import com.depanker.currencyconverter.services.CurrencyConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class CurrencyConverterController {

    private final CurrencyConverter currencyConverterImpl;

    @PostMapping(value = "/currency/convert", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    Mono<ConversionResponse> conversionResponse(@RequestBody @Valid ConversionRequest conversionRequest) {
        return currencyConverterImpl.getConversionResponse(conversionRequest);
    }
}
