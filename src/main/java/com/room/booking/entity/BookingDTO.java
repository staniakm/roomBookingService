package com.room.booking.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BookingDTO {

    private String customerName;
    private String customerSurname;
    private String customerEmail;
    private LocalDate dateFrom;
    private LocalDate dateTo;

}
