package com.room.booking.exception;

public class CancelOtherCustomerBookingException extends RuntimeException{

    public CancelOtherCustomerBookingException(String message) {
        super(message);
    }
}
