package com.room.booking.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    @OneToOne
    @JoinColumn(name = "ROOM_ID")
    @NotNull
    private Room room;
    @OneToOne
    @JoinColumn(name = "CUSTOMER_ID")
    @NotNull
    private Customer customer;
    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;
    @NotNull
    private LocalDate dateFrom;
    @NotNull
    private LocalDate dateTo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return room.getRoomNumber().equals(booking.room.getRoomNumber()) &&
                customer.getEmailAddress().equals(booking.customer.getEmailAddress()) &&
                bookingStatus == booking.bookingStatus &&
                dateFrom.equals(booking.dateFrom) &&
                dateTo.equals(booking.dateTo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(room.getRoomNumber(), customer.getEmailAddress(), bookingStatus, dateFrom, dateTo);
    }
}
