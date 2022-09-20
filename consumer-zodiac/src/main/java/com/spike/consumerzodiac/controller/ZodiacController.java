package com.spike.consumerzodiac.controller;

import java.util.HashMap;
import java.util.Map;

import com.spike.consumerzodiac.service.ZodiacService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consumer")
public class ZodiacController {
    private final ZodiacService zodiacService;

    public ZodiacController(ZodiacService zodiacService) {
        this.zodiacService = zodiacService;
    }

    @GetMapping(value = "/chinese-zodiac",
            produces = {"application/json"})
    public ResponseEntity getChineseZodiac(@RequestParam(name = "birthDate") String birthDate) {
        //LocalDate birthday = zodiacService.validateBirthdate(birthDate);
        String sign = zodiacService.getChineseZodiac(birthDate);
        if (sign != null) {
            return ResponseEntity.ok().body(sign);
        } else {
            return ResponseEntity.badRequest().body(new HashMap<>(){{put("status","400");put("Message","Your input is invalid");}});
        }
    }

}
