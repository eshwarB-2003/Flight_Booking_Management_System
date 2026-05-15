package com.flightbooking.BookingService.repository;

import com.flightbooking.BookingService.entity.BookingAncillary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingAncillaryRepository
        extends JpaRepository<BookingAncillary, Long> {

    List<BookingAncillary> findByBookingId(Long bookingId);
}