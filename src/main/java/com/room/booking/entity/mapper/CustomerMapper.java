package com.room.booking.entity.mapper;

import com.room.booking.entity.BookingDTO;
import com.room.booking.entity.Customer;

public class CustomerMapper {


    public static Customer toEntity(BookingDTO bookingDTO) {
        return Customer.builder()
                .emailAddress(bookingDTO.getCustomerEmail())
                .name(bookingDTO.getCustomerName())
                .surname(bookingDTO.getCustomerSurname())
                .build();
    }
}
