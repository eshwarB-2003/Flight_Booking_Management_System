package com.flightbooking.BookingService.repository;

import com.flightbooking.BookingService.entity.SeatReservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatReservationRepository
        extends JpaRepository<SeatReservation, Long> {
}