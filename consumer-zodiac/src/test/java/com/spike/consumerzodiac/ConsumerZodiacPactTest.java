package com.spike.consumerzodiac;

import java.util.Collections;
import java.util.List;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import com.spike.consumerzodiac.model.DateResponse;
import com.spike.consumerzodiac.service.ZodiacService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(PactConsumerTestExt.class)
public class ConsumerZodiacPactTest {

    @Pact(consumer = "Consumer-Zodiac", provider = "Provider-Date-Validate")
    RequestResponsePact getZodiacSign(PactDslWithProvider builder) {
        return builder.given("valid date from provider")
                .uponReceiving("validate the date")
                .method("GET")
                .path("/date-validate")
                .queryMatchingDate("Date", "1994-04-25" )
                .willRespondWith()
                .status(200)
                .body(new PactDslJsonBody()
                        .object("DateResponse")
                        .integerType("year", 1994)
                        .integerType("month", 04)
                        .integerType("day", 25)
                        .booleanType("isValidDate", true)
                        .closeObject())
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "getZodiacSign")
    void getZodiacSign_whenValidDateProvidedToConsumer(MockServer mockServer) {
        RestTemplate restTemplate = new RestTemplateBuilder()
                .rootUri(mockServer.getUrl())
                .build();
        String sign = new ZodiacService(restTemplate).getChineseZodiac("1994-08-09");

        assertEquals("Dog", sign);
    }
}
