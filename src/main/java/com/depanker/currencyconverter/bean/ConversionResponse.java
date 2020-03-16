package com.depanker.currencyconverter.bean;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConversionResponse extends ConversionRequest {
    @Setter(AccessLevel.NONE)
    Double converted;
    public void setConverted(Double converted) {
        this.converted =   Math.round(converted * 100.0) / 100.0;
    }
}
