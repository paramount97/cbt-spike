package com.spike.consumerzodiac.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DateResponse {
    private int year;
    private int month;
    private int day;
    private Boolean isValidDate = false;
}