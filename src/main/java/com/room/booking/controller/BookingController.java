package com.room.booking.controller;

import com.room.booking.entity.BookingDTO;
import com.room.booking.entity.Room;
import com.room.booking.service.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/booking")
@AllArgsConstructor
public class BookingController {

    private final RoomService roomService;

    @GetMapping("/rooms")
    public ResponseEntity<List<Room>> getAvailableRooms(
            @RequestParam(value = "start")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                    LocalDate dateFrom,
            @RequestParam(value = "end")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                    LocalDate dateTo
    ) {
        return new ResponseEntity<>(roomService.findAvailableRooms(dateFrom, dateTo), HttpStatus.OK);
    }

    @PostMapping("/{roomId}/book")
    public ResponseEntity<Room> bookRoom(@PathVariable("roomId") Long roomId, @RequestBody BookingDTO bookingDTO) {
        final Room room = roomService.bookRoom(roomId, bookingDTO);
        return new ResponseEntity<>(room, HttpStatus.OK);
    }

}
