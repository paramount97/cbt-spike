package com.spike.providerdatevalidate.controller;

import com.spike.providerdatevalidate.model.DateResponse;
import com.spike.providerdatevalidate.service.DateValidatorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/date-validate")
public class DateValidatorController {

    @Autowired
    private DateValidatorService dateValidatorService;

    @GetMapping("")
    public DateResponse validateDate(@RequestParam(name = "Date") String date){
        return dateValidatorService.validateDate(date);
    }


}
