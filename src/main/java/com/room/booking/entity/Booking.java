package com.room.booking.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    @OneToOne
    @JoinColumn(name = "ROOM_ID")
    private Room room;
    @OneToOne
    @JoinColumn(name = "CUSTOMER_ID")
    private Customer customer;
    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private Status bookingStatus;
    private LocalDate dateFrom;
    private LocalDate dateTo;

}