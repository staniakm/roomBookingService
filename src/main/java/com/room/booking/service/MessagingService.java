package com.room.booking.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class MessagingService {

    private final BookingService bookingService;
    private final MessageClient messageClient;

    void sendReminder(ReminderType reminderType) {
        switch (reminderType) {
            case BOOKING_DATE:
                sendMessageAboutBookingDate();
                break;
            case CONFIRMATION:
                sendMessageAboutConfirmation();
                break;
            default:
                throw new IllegalArgumentException("Provided reminder type is incorrect");
        }
    }

    private void sendMessageAboutBookingDate() {
        String message = "Your booking starts tomorrow";
        bookingService.findAllUpcomingBookings()
                .forEach(booking -> messageClient.sendMessage(booking.getCustomer(), message));
    }

    private void sendMessageAboutConfirmation() {
        String message = "Your booking is unconfirmed";
        bookingService.findAllUnconfirmed()
                .forEach(booking -> messageClient.sendMessage(booking.getCustomer(), message));
    }
}
