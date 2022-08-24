package com.spike.providerdatevalidate.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DateResponse {
    private int year = 0;
    private int month = 0;
    private int day = 0;
    private Boolean isValidDate = false;
}
