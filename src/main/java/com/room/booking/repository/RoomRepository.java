package com.room.booking.repository;

import com.room.booking.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query("select r from Room r where not exists (select 1 from  Booking b where b.room = r " +
            "and (b.dateTo >= (:dateFrom) and  b.dateFrom <= (:dateTo))  and status in ('CREATED','CONFIRMED'))")
    List<Room> findAllAvailableRoomsBetweenDates(LocalDate dateFrom, LocalDate dateTo);


    @Query("select r from Room r where r.roomId = (:roomId)" +
            " and not exists (select 1 from  Booking b where b.room = r " +
            "and (b.dateTo >= (:dateFrom) and  b.dateFrom <= (:dateTo))  and status in ('CREATED','CONFIRMED'))"
    )
    Optional<Room> findIfAvailable(Long roomId, LocalDate dateFrom, LocalDate dateTo);
}
