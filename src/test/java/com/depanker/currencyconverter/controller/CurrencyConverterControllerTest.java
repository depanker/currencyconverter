package com.depanker.currencyconverter.controller;

import com.depanker.currencyconverter.bean.ConversionRequest;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.ManualRestDocumentation;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.event.annotation.AfterTestMethod;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.documentationConfiguration;

@SpringBootTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
class CurrencyConverterControllerTest {
    private WebTestClient webTestClient;
    @BeforeEach
    public void setUp(ApplicationContext applicationContext,
                      RestDocumentationContextProvider restDocumentation) {
        this.webTestClient = WebTestClient.bindToApplicationContext(applicationContext)
                .configureClient()
                .filter(documentationConfiguration(restDocumentation).operationPreprocessors()
                        .withRequestDefaults(prettyPrint())
                        .withResponseDefaults(prettyPrint()))
                .build();
    }

    @Test
    void conversionResponse() {
        webTestClient.post()
                .uri("/currency/convert")
                .body(Mono.just(new ConversionRequest("EUR", "USD", 3.14)), ConversionRequest.class)
                .accept(MediaType.APPLICATION_JSON)
                .exchange().expectStatus().isOk()
        .expectBody().consumeWith(document("conversionResponse"));
    }

    @Test
    void conversionBadRequestNullValuesResponse() {
        webTestClient.post()
                .uri("/currency/convert")
                .body(Mono.just(new ConversionRequest()), ConversionRequest.class)
                .accept(MediaType.APPLICATION_JSON)
                .exchange().expectStatus().isBadRequest()
                .expectBody().consumeWith(document("conversionBadRequestNullValuesResponse"));
    }

    @Test
    void conversionBadRequestInvalidValuesResponse() {
        ConversionRequest data = new ConversionRequest();
        data.setAmount(0.0);
        data.setFrom("EURO");
        data.setTo("USD ");
        webTestClient.post()
                .uri("/currency/convert")
                .body(Mono.just(data), ConversionRequest.class)
                .accept(MediaType.APPLICATION_JSON)
                .exchange().expectStatus().isBadRequest()
                .expectBody().consumeWith(document("conversionBadRequestInvalidValuesResponse"));
    }

    @Test
    void getErrorRequest() {
        ConversionRequest data = new ConversionRequest();
        data.setAmount(3.3);
        data.setFrom("XYZ");
        data.setTo("USD");
        webTestClient.post()
                .uri("/currency/convert")
                .body(Mono.just(data), ConversionRequest.class)
                .accept(MediaType.APPLICATION_JSON)
                .exchange().expectStatus().is5xxServerError()
                .expectBody().consumeWith(document("getErrorRequest"));
    }
}