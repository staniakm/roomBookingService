package com.room.booking.service;

import com.room.booking.entity.*;
import com.room.booking.entity.mapper.CustomerMapper;
import com.room.booking.repository.BookingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class BookingService {

    private final RoomService roomService;
    private final CustomerService customerService;
    private BookingRepository bookingRepository;

    public List<Room> findAvailableRooms(LocalDate dateFrom, LocalDate dateTo) {
        return roomService.findAvailableRooms(dateFrom, dateTo);
    }

    public Booking bookRoom(Long roomId, BookingDTO bookingDTO) {
        final LocalDate dateFrom = bookingDTO.getDateFrom();
        final LocalDate dateTo = bookingDTO.getDateTo();
        Room room = roomService.getRoomIfAvailable(roomId, dateFrom, dateTo);

        Customer mappedCustomer = CustomerMapper.toEntity(bookingDTO);
        Customer customer = customerService.getCustomer(mappedCustomer);

        final Booking booking = Booking.builder()
                .bookingStatus(Status.CREATED)
                .customer(customer)
                .room(room)
                .dateFrom(dateFrom)
                .dateTo(dateTo)
                .build();

        return bookingRepository.save(booking);

    }
}
