package com.spike.providerdatevalidate.service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import com.spike.providerdatevalidate.model.DateResponse;

import org.springframework.stereotype.Service;

@Service
public class DateValidatorService {

    public DateResponse validateDate(String birthDate){
        try {
            LocalDate date = LocalDate.parse(birthDate);
            date.plusDays(1);
            DateResponse response = new DateResponse(date.getYear(), date.getMonthValue(), date.getDayOfMonth(), true);
            return response;
        } catch (DateTimeParseException e) {
            return new DateResponse();
        }
    }
}
