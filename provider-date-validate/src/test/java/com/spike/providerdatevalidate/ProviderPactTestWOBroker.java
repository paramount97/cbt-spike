package com.spike.providerdatevalidate;

import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Consumer;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactBroker;
import au.com.dius.pact.provider.junitsupport.loader.PactBrokerAuth;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@Provider("Provider-Date-Validate")
@Consumer("Consumer-Zodiac")
@PactFolder("pacts")
//@PactBroker(
//        url = "http://10.164.13.41:9292/",
////        url = "https://nbscop.pactflow.io/",
//        providerBranch = "main",
//        enablePendingPacts = "true",
//        authentication = @PactBrokerAuth(token= "RoYnhMmcBDaNVBrZZqIN7Q")
//)
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProviderPactTestWOBroker {


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
//        System.setProperty("pact.provider.tag", "test");
    }

    @BeforeEach
    void before(PactVerificationContext context) {
        context.setTarget(new HttpTestTarget("localhost", 8083));
    }

    @State("valid date from provider")
    public void toValidState() {
    }

    @State("Invalid date from provider")
    public void toInvalidState() { }

}
