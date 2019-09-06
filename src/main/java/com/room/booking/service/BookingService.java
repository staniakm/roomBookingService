package com.room.booking.service;

import com.room.booking.entity.*;
import com.room.booking.entity.mapper.CustomerMapper;
import com.room.booking.exception.CancelOtherCustomerBookingException;
import com.room.booking.exception.EntityNotExistsException;
import com.room.booking.repository.BookingRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

@Service
@AllArgsConstructor
@Slf4j
public class BookingService {

    private final RoomService roomService;
    private final CustomerService customerService;
    private final BookingRepository bookingRepository;

    public List<Room> findAvailableRooms(LocalDate dateFrom, LocalDate dateTo) {
        return roomService.findAvailableRooms(dateFrom, dateTo);
    }

    public Booking bookRoom(Long roomId, BookingDTO bookingDTO) {
        log.info("Start creating booking for room {}", roomId);
        final LocalDate dateFrom = bookingDTO.getDateFrom();
        final LocalDate dateTo = bookingDTO.getDateTo();
        if (isNull(dateFrom) || isNull(dateTo)) {
            throw new IllegalArgumentException("Both starting date and ending date should be provided");
        }
        Room room = roomService.getRoomIfAvailable(roomId, dateFrom, dateTo);

        Customer mappedCustomer = CustomerMapper.toEntity(bookingDTO);
        Customer customer = customerService.getExistingCustomerOrCreateNew(mappedCustomer);

        final Booking booking = Booking.builder()
                .bookingStatus(BookingStatus.CREATED)
                .customer(customer)
                .room(room)
                .dateFrom(dateFrom)
                .dateTo(dateTo)
                .build();
        log.info("Booking created for customer {}", customer.getCustomerId());
        return bookingRepository.save(booking);

    }

    public Booking cancelBooking(Long bookingId, BookingDTO bookingDTO) {
        final Optional<Booking> bookingById = bookingRepository.findById(bookingId);
        if (!bookingById.isPresent()) {
            log.warn("Booking {} doesnt exists", bookingId);
            throw new EntityNotExistsException("Booking doesn't exists");

        }
        Booking booking = bookingById.get();

        final Optional<Customer> customerByEmail = customerService.getCustomerByEmail(bookingDTO.getCustomerEmail());
        if (!customerByEmail.isPresent()) {
            log.warn("Customer with email {} doesn't exists", bookingDTO.getCustomerEmail());
            throw new EntityNotExistsException("Customer doesn't exists");
        }
        Customer customer = customerByEmail.get();
        if (booking.getCustomer().equals(customer)) {
            booking.setBookingStatus(BookingStatus.CANCELLED);
            final Booking cancelled = bookingRepository.save(booking);
            log.info("Booking {} cancelled for customer {}", booking.getBookingId(), customer.getCustomerId());
            return cancelled;
        } else
            throw new CancelOtherCustomerBookingException("Can't cancel others booking");
    }

    List<Booking> findAllUnconfirmed() {
        return bookingRepository.findAllByBookingByStatus(BookingStatus.CREATED);
    }

    List<Booking> findAllUpcomingBookings() {
        return bookingRepository.findAllUpcomingBookings(LocalDate.now().plusDays(1));
    }
}
