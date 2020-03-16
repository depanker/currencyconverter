package com.depanker.currencyconverter.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConversionRequest {
    @NotBlank
    @Size(min = 3, max = 3, message = "Size must be equal to 3")
    private String from;
    @NotBlank
    @Size(min = 3, max = 3,  message = "Size must be equal to 3")
    private String to;
    @NotNull
    @DecimalMin("0.01")
    private Double amount;
}
