package com.room.booking.service;

import com.room.booking.entity.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ConsoleInfoMessageReminder implements MessageClient {

    @Override
    public void sendMessage(Customer customer, String message) {
        log.info("Sending message to customer: {}", customer.getEmailAddress());
        log.info(message);
    }
}
