package com.spike.providerdatevalidate;

import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Consumer;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactBroker;
import au.com.dius.pact.provider.junitsupport.loader.PactBrokerAuth;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@Provider("Provider-Date-Validate")
@Consumer("Consumer-Zodiac")
//@PactFolder("pacts")
@PactBroker(
        //url = "http://localhost:9292/"-----------DOCKERIZED PACT BROKER
        url = "https://nbscop.pactflow.io/",
        providerBranch = "main",
        enablePendingPacts = "true",
        authentication = @PactBrokerAuth(token= "RoYnhMmcBDaNVBrZZqIN7Q")
)
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProviderDatePactTest {


    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void pactVerificationTestTemplate(PactVerificationContext context) {
        context.verifyInteraction();
    }

    @BeforeAll
    static void enablePublishingPact() {
        System.setProperty("pact.verifier.publishResults", "true");
        System.setProperty("pact.provider.version", "yf495h");
        System.setProperty("pact.provider.branch", "main");
        System.setProperty("pact.provider.tag", "test");
    }

    @BeforeEach
    void before(PactVerificationContext context) {
        context.setTarget(new HttpTestTarget("localhost", 8080));
    }

    @State("User sends valid date")
    public void toValidState() {
    }

    @State("User sends invalid date")
    public void toInvalidState() { }

}
