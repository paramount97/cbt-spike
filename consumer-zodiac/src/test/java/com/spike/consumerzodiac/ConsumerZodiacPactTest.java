package com.spike.consumerzodiac;

import java.io.IOException;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import com.jayway.jsonpath.JsonPath;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(PactConsumerTestExt.class)
public class ConsumerZodiacPactTest {

    @BeforeAll
    static void pactProperties() {
       // System.setProperty("pact.writer.overwrite", "true");
//        System.setProperty("pact.verifier.publishResults", "true");
//        System.setProperty("pact.provider.version", "1.3");
//        System.setProperty("pact.provider.tag", "main");
//        System.setProperty("pact.provider.tag", "Test");
    }

    @Pact(consumer = "Consumer-Zodiac", provider = "Provider-Date-Validate")
    RequestResponsePact getValidDate(PactDslWithProvider builder) {
        JSONObject jo = new JSONObject();
        jo.put("year", 1994);
        jo.put("month", 04);
        jo.put("day", 25);
        jo.put("isValidDate", true);
        return builder
                .given("User sends valid date")
                .uponReceiving("Provider should return date as valid")
                    .method("GET")
                    .path("/date-validate")
                    .queryMatchingDate("Date", "1994-04-25" )
                .willRespondWith()
                    .status(200)
                    .body(new PactDslJsonBody()
                            .integerType("year", 1994)
                            .integerType("month", 04)
                            .integerType("day", 25)
                            .booleanType("isValidDate", true))
                .toPact();
    }

    @Pact(consumer = "Consumer-Zodiac", provider = "Provider-Date-Validate")
    RequestResponsePact getInvalidDate(PactDslWithProvider builder) {
        JSONObject jo = new JSONObject();
        jo.put("year", 0);
        jo.put("month", 0);
        jo.put("day", 0);
        jo.put("isValidDate", false);
        return builder
                .given("User sends invalid date")
                .uponReceiving("Provider should return date as invalid")
                    .method("GET")
                    .path("/date-validate")
                    .queryMatchingDate("Date", "1000-34-45" )
                .willRespondWith()
                    .status(200)
                    .body(new PactDslJsonBody()
                            .integerType("year", 0)
                            .integerType("month", 0)
                            .integerType("day", 0)
                            .booleanType("isValidDate", false))
                .toPact();
    }


    @Test
    @PactTestFor(pactMethod = "getValidDate")
    void Should_Return_True_When_Valid_Date_Provided(MockServer mockServer)  throws IOException {
        HttpResponse httpResponse = Request.Get(mockServer.getUrl() + "/date-validate?Date=1994-04-25")
                .execute().returnResponse();

        String response = httpResponse.getEntity().getContent().toString();

        assertThat(httpResponse.getStatusLine().getStatusCode()).isEqualTo(200);
        assertThat(JsonPath.read(httpResponse.getEntity().getContent(), "$.isValidDate").toString()).isEqualTo("true");
    }

    @Test
    @PactTestFor(pactMethod = "getInvalidDate")
    void Should_Return_False_When_Invalid_Date_Provided(MockServer mockServer)  throws IOException {
        HttpResponse httpResponse = Request.Get(mockServer.getUrl() + "/date-validate?Date=1000-34-45")
                .execute().returnResponse();

        assertThat(httpResponse.getStatusLine().getStatusCode()).isEqualTo(200);
        assertThat(JsonPath.read(httpResponse.getEntity().getContent(), "$.isValidDate").toString()).isEqualTo("false");
    }
}


///TODO: Look into different ways of writing PACT method (some are more readable and compact than others). REF: https://medium.com/swlh/contract-tests-with-pact-jvm-the-tricky-parts-6b2fee5d139c
