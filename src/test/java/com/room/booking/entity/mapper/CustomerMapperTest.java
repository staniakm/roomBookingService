package com.room.booking.entity.mapper;

import com.room.booking.entity.BookingDTO;
import com.room.booking.entity.Customer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerMapperTest {

    @Test
    public void shouldCreateCustomerFromBookingDto() throws Exception {
        //given
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setCustomerEmail("email");
        bookingDTO.setCustomerName("name");
        bookingDTO.setCustomerSurname("surname");

        //when
        final Customer customer = CustomerMapper.toEntity(bookingDTO);

        //then
        assertEquals("email", customer.getEmailAddress());
        assertEquals("name", customer.getName());
        assertEquals("surname", customer.getSurname());
    }
}