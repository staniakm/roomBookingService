package com.room.booking.controller;

import com.room.booking.entity.Room;
import com.room.booking.service.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/booking")
@AllArgsConstructor
public class BookingController {

    private final RoomService roomService;

    @GetMapping("/rooms")
    public ResponseEntity<List<Room>> getAvailableRooms() {
        return new ResponseEntity<>(roomService.findAvailableRooms(), HttpStatus.OK);
    }

}
