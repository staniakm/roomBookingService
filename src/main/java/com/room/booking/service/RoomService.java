package com.room.booking.service;

import com.room.booking.entity.BookingDTO;
import com.room.booking.entity.Room;
import com.room.booking.repository.RoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.CollectionHelper.newArrayList;

@Service
@AllArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    public Room bookRoom(Long id, BookingDTO customer) {
        final Optional<Room> optionalRoom = roomRepository.findById(id);
        if (optionalRoom.isPresent()) {
            Room room = optionalRoom.get();


        }
        return null;
    }

    public List<Room> findAvailableRooms() {
        return roomRepository.findAllAvailableRoomsBetweenDates(LocalDate.now(), LocalDate.now().plusDays(10));
    }
}
