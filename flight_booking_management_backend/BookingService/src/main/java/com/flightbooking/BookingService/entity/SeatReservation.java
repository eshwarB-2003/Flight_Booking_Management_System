package com.flightbooking.BookingService.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "seat_reservation")
@Data
public class SeatReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;

    private Long bookingId;

    private Long seatId;

    private String reservationStatus;

    private LocalDateTime lockedAt;

    private LocalDateTime confirmedAt;

    private LocalDateTime releasedAt;
}