package com.room.booking.entity;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Getter
@Setter
public class BookingDTO {

    private String customerName;
    private String customerSurname;
    @NotBlank
    private String customerEmail;
    private LocalDate dateFrom;
    private LocalDate dateTo;

}
