package com.room.booking.controller;

import com.room.booking.entity.Booking;
import com.room.booking.entity.BookingDTO;
import com.room.booking.entity.Room;
import com.room.booking.service.BookingService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bookings")
@AllArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @GetMapping(value = "/rooms", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Room>> getAvailableRooms(
            @RequestParam(value = "start", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                    Optional<LocalDate> optionalDateFrom,
            @RequestParam(value = "end", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                    Optional<LocalDate> optionalDateTo
    ) {

        LocalDate dateFrom = optionalDateFrom.orElseGet(LocalDate::now);
        LocalDate dateTo = optionalDateTo.orElseGet(() -> dateFrom.plusDays(2));
        return new ResponseEntity<>(bookingService.findAvailableRooms(dateFrom, dateTo), HttpStatus.OK);
    }

    @PostMapping(value = "/{roomId}/book", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Booking> bookRoom(@PathVariable("roomId") Long roomId, @RequestBody BookingDTO bookingDTO) {
        final Booking booking = bookingService.bookRoom(roomId, bookingDTO);
        return new ResponseEntity<>(booking, HttpStatus.CREATED);
    }

    @PostMapping("/{bookingId}/cancel")
    public ResponseEntity<Booking> cancelBooking(@PathVariable Long bookingId, @RequestBody BookingDTO bookingDTO) {
        Booking cancelledBooking = bookingService.cancelBooking(bookingId, bookingDTO);
        return new ResponseEntity<>(cancelledBooking, HttpStatus.OK);
    }

}
