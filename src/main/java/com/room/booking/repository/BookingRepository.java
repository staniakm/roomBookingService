package com.room.booking.repository;

import com.room.booking.entity.Booking;
import com.room.booking.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("select b from Booking b join fetch b.customer where b.bookingStatus = (:status)")
    List<Booking> findAllByBookingByStatus(Status status);

    @Query("select b from Booking b join fetch b.customer where b.bookingStatus = 'CONFIRMED' " +
            "and b.dateFrom=(:date)")
    List<Booking> findAllUpcomingBookings(LocalDate date);
}
