package com.spike.consumerzodiac;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class ConsumerZodiacApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerZodiacApplication.class, args);
    }

    @Bean
    public WebClient.Builder getWebClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    RestTemplate getRestTemplate() {
        return new RestTemplateBuilder().build();
    }

}
