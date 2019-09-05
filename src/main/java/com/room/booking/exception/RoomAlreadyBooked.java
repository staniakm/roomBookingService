package com.room.booking.exception;

public class RoomAlreadyBooked extends RuntimeException{

    public RoomAlreadyBooked(String message) {
        super(message);
    }
}
