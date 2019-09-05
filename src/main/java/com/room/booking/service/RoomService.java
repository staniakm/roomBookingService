package com.room.booking.service;

import com.room.booking.entity.BookingDTO;
import com.room.booking.entity.Room;
import com.room.booking.repository.RoomRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.CollectionHelper.newArrayList;

@Service
@AllArgsConstructor
@Slf4j
public class RoomService {

    private final RoomRepository roomRepository;

    public Room bookRoom(Long roomId, BookingDTO bookingDTO) {
        final LocalDate dateFrom = bookingDTO.getDateFrom();
        final LocalDate dateTo = bookingDTO.getDateTo();
        log.info("room: {} dateFrom: {} dateTo: {}", roomId, dateFrom, dateTo);

        final Optional<Room> optionalRoom = roomRepository.findIfAvailable(roomId, dateFrom, dateTo);
        if (optionalRoom.isPresent()) {
            log.info("Room {} is available", roomId);
            Room room = optionalRoom.get();


        }
        return null;
    }

    public List<Room> findAvailableRooms(LocalDate dateFrom, LocalDate dateTo) {
        log.info("Searching available rooms between dates {} and {} ", dateFrom, dateTo);
        if (dateFrom.isAfter(dateTo)) {
            throw new IllegalArgumentException("Ending date must be greater than starting");
        }
        return roomRepository.findAllAvailableRoomsBetweenDates(dateFrom, dateTo);
    }
}
