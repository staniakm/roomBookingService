package com.room.booking.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class ScheduleService {

    private final MessagingService messagingService;

    /**
     * scheduler responsible sending reminder about booking everyday as 15:00
     */
    @Scheduled(cron = "0 0 15 * * ?")
    public void bookingReminder() {
        log.info("Schedule for booking reminder started....");
        messagingService.sendReminder(ReminderType.BOOKING_DATE);
    }

    /**
     * scheduler responsible sending reminder about confirmation of booking everyday as 8:00
     */
    @Scheduled(cron = "0 0 8 * * ?")
    public void confirmationScheduler() {
        log.info("Scheduler for booking confirmation reminder started....");
        messagingService.sendReminder(ReminderType.CONFIRMATION);
    }

}
