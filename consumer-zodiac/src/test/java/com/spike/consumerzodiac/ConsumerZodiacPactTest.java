package com.spike.consumerzodiac;

import java.io.IOException;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import com.jayway.jsonpath.JsonPath;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(PactConsumerTestExt.class)
public class ConsumerZodiacPactTest {

    @Pact(consumer = "Consumer-Zodiac", provider = "Provider-Date-Validate")
    RequestResponsePact getValidDate(PactDslWithProvider builder) {
        JSONObject jo = new JSONObject();
        jo.put("year", 1994);
        jo.put("month", 04);
        jo.put("day", 25);
        jo.put("isValidDate", true);
        return builder.given("valid date from provider")
                .uponReceiving("validate the correct date")
                .method("GET")
                .path("/date-validate")
                .queryMatchingDate("Date", "1994-04-25" )
                .willRespondWith()
                .status(200)
                .body(jo)
                .toPact();
    }

    @Pact(consumer = "Consumer-Zodiac", provider = "Provider-Date-Validate")
    RequestResponsePact getInvalidDate(PactDslWithProvider builder) {
        JSONObject jo = new JSONObject();
        jo.put("year", 1000);
        jo.put("month", 34);
        jo.put("day", 45);
        jo.put("isValidDate", false);
        return builder.given("Invalid date from provider")
                .uponReceiving("validate the invalid date")
                .method("GET")
                .path("/date-validate")
                .queryMatchingDate("Date", "1000-34-45" )
                .willRespondWith()
                .status(400)
                .body(jo)
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

        assertThat(httpResponse.getStatusLine().getStatusCode()).isEqualTo(400);
        assertThat(JsonPath.read(httpResponse.getEntity().getContent(), "$.isValidDate").toString()).isEqualTo("false");
    }
}


///TODO: Look into different ways of wring PACT method. REF: https://medium.com/swlh/contract-tests-with-pact-jvm-the-tricky-parts-6b2fee5d139c
