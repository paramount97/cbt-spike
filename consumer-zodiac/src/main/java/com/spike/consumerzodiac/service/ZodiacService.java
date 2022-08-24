package com.spike.consumerzodiac.service;

import java.time.LocalDate;

import com.spike.consumerzodiac.model.DateResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class ZodiacService {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Value("${dateprovidervalidator.baseurl}")
    private String dateProviderServiceURL;

    public String getChineseZodiac(String birthDate) {
        String uri = UriComponentsBuilder.fromHttpUrl(dateProviderServiceURL)
                .path("date-validate")
                .queryParam("Date", birthDate)
                .toUriString();
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", "application/json");

        ResponseEntity<DateResponse> responseEntity = new RestTemplate().exchange(uri, HttpMethod.GET,
                new HttpEntity<>(headers), DateResponse.class);
//        DateResponse response = webClientBuilder.get()
//                .uri(uri)
//                .header("content-type", "application/json")
//                .retrieve()
//                .build();

        if(responseEntity.getBody().getIsValidDate()) {
            LocalDate birthday = LocalDate.of(responseEntity.getBody().getYear(), responseEntity.getBody().getMonth(), responseEntity.getBody().getDay());
            return getZodiacSign(birthday);
        }
        else{
            return null;
        }
    }

    public String getZodiacSign(LocalDate birthday) {
        int year = birthday.getYear();
        switch (year % 12) {
            case 0:
                return "Monkey";
            case 1:
                return "Rooster";
            case 2:
                return "Dog";
            case 3:
                return "Pig";
            case 4:
                return "Rat";
            case 5:
                return "Ox";
            case 6:
                return "Tiger";
            case 7:
                return "Rabbit";
            case 8:
                return "Dragon";
            case 9:
                return "Snake";
            case 10:
                return "Horse";
            case 11:
                return "Sheep";
        }
        return "";
    }
}
