package com.depanker.currencyconverter.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Map;

@Getter
@Setter
public class ExchangeRates {
    Map<String, Float> rates;
    String base;
    @JsonProperty("date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-M-d")
    Date date;
}
