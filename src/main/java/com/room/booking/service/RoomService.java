package com.room.booking.service;

import com.room.booking.entity.Room;
import com.room.booking.exception.RoomAlreadyBookedException;
import com.room.booking.repository.RoomRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class RoomService {

    private final RoomRepository roomRepository;

    Room getRoomIfAvailable(Long roomId, LocalDate dateFrom, LocalDate dateTo) {
        log.info("room: {} dateFrom: {} dateTo: {}", roomId, dateFrom, dateTo);

        final Optional<Room> optionalRoom = roomRepository.findIfAvailable(roomId, dateFrom, dateTo);
        if (optionalRoom.isPresent()) {
            log.info("Room {} is available", roomId);
            return optionalRoom.get();
        }
        log.info("Room {} is not available", roomId);
        throw new RoomAlreadyBookedException(String.format("Selected room is not available for date range %s and %s", dateFrom, dateTo));
    }

    List<Room> findAvailableRooms(LocalDate dateFrom, LocalDate dateTo) {
        log.info("Searching available rooms between dates {} and {} ", dateFrom, dateTo);
        if (dateFrom.isAfter(dateTo)) {
            throw new IllegalArgumentException("Ending date must be greater than starting");
        }
        return roomRepository.findAllAvailableRoomsBetweenDates(dateFrom, dateTo);
    }
}
