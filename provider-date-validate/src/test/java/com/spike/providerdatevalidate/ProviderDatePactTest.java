package com.spike.providerdatevalidate;

import au.com.dius.pact.provider.junit.Consumer;
import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactBroker;
import au.com.dius.pact.provider.junit.loader.PactBrokerAuth;
import au.com.dius.pact.provider.junit.loader.PactFolder;
import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import com.spike.providerdatevalidate.model.DateResponse;
import com.spike.providerdatevalidate.service.DateValidatorService;
import org.apache.http.HttpRequest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.ConfigurableWebApplicationContext;

import static org.mockito.Mockito.when;

@Provider("Provider-Date-Validate")
@Consumer("Consumer-Zodiac")
//@PactFolder("pacts")
@PactBroker(
        //host = "localhost",
        //port = "9292"
        scheme = "https",
        host = "nbscop.pactflow.io/",
        //tags = {"main", "ds4550f"},
        //consumerVersionSelectors = {@VersionSelector(tag = "dev"), @VersionSelector(tag = "master"), @VersionSelector(tag = "test")}
        authentication = @PactBrokerAuth(token= "RoYnhMmcBDaNVBrZZqIN7Q")
        //authentication = @PactBrokerAuth(scheme ="bearer", username= "RoYnhMmcBDaNVBrZZqIN7Q", password = "Nationwide")
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
        System.setProperty("pact.provider.version", "ds495h");
        System.setProperty("pact.provider.providerBranch", "main");
        //System.setProperty("pact.provider.tag", "dcop-3161");
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
