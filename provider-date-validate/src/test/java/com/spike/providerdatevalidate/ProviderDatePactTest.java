package com.spike.providerdatevalidate;

import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
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
@PactFolder("pacts")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProviderDatePactTest {

    private static ConfigurableWebApplicationContext application;

    @MockBean
   private DateValidatorService dateValidator;

    @LocalServerPort
    int port;

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void pactVerificationTestTemplate(PactVerificationContext context) {
        context.verifyInteraction();
    }

//    @BeforeAll
//    public static void start() {
//        application = (ConfigurableWebApplicationContext) SpringApplication.run(ProviderDateValidateApplication.class);
//    }

    @BeforeEach
    void before(PactVerificationContext context) {
        context.setTarget(new HttpTestTarget("localhost", port, "/date-validate"));
    }

    @State("valid date from provider")
    public void toValidState() {
        when(dateValidator.validateDate("1996-10-09")).thenReturn(new DateResponse(1996, 10, 9, true ));
    }

    @State("Invalid date from provider")
    public void toInvalidState() { }

//    @MockBean
//    private DateValidatorService dateValidator;
//
//    @BeforeEach
//    void setUp(PactVerificationContext context) {
//        context.setTarget(new HttpTestTarget("localhost", port));
//    }
//
//    @TestTemplate
//    @ExtendWith(PactVerificationInvocationContextProvider.class)
//    void verifyPact(PactVerificationContext context, HttpRequest request) {
//        replaceAuthHeader(request);
//        context.verifyInteraction();
//    }
}
