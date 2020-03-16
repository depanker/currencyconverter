package com.depanker.currencyconverter.exception;


import com.depanker.currencyconverter.bean.exception.GenericErrorMessage;
import com.depanker.currencyconverter.bean.exception.InputValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_GATEWAY;

@ControllerAdvice(annotations = RestController.class)
@Slf4j
public class ExceptionAdvice {

    @ExceptionHandler(WebClientResponseException.class)
    @ResponseStatus(BAD_GATEWAY)
    @ResponseBody
    List<GenericErrorMessage> handleBadInputException(WebClientResponseException e, HandlerMethod handlerMethod) {
        List<GenericErrorMessage> errors = Arrays.asList(new GenericErrorMessage("No provider available at the moment," +
                " please try again after some time."));
        log.error(e.getMessage(), e);
        return errors;
    }

    @ExceptionHandler(ResponseStatusException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    List<InputValidation> handleBadInputException(WebExchangeBindException e, HandlerMethod handlerMethod) {
        List<InputValidation> errors =  e.getBindingResult().getFieldErrors().stream()
                .map(error -> new InputValidation(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());
        log.warn("Input validation error occurred while request from {}.{}(), error: {}", handlerMethod.getBeanType()
                .getSimpleName(), handlerMethod.getMethod().getName(), errors.toString());
        return errors;
    }
}
