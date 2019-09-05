package com.room.booking.service;

import com.room.booking.entity.Customer;

public interface MessageClient {

    void sendMessage(Customer customer, String message);
}
