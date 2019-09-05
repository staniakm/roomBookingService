package com.room.booking.repository;

import com.room.booking.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface RoomRepository extends JpaRepository<Room, Long> {
}
