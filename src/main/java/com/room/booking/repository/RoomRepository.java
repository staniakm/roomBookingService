package com.room.booking.repository;

import com.room.booking.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query("select r from Room r left join Book b on b.room = r where (b.bookingStatus is null or b.bookingStatus in ('CANCELLED'))" +
            " ")
    List<Room> findAllAvailableRoomsBetweenDates(LocalDate dateFrom, LocalDate endDate);



}
