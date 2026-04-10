package com.flightbooking.BookingService.factory;

import com.flightbooking.BookingService.entity.Booking;

import java.time.LocalDateTime;
import java.util.UUID;

public class BookingFactory {

    public static Booking create(
            Long userId,
            Long flightId,
            Double totalAmount
    ) {

        Booking booking = new Booking();

        booking.setUserId(userId);
        booking.setFlightId(flightId);
        booking.setTotalAmount(totalAmount);

        booking.setBookingStatus("REQUESTED");

        booking.setBookingReference(generateReference());

        booking.setCreatedAt(LocalDateTime.now());

        return booking;
    }

    private static String generateReference() {
        return "FLY-" + UUID.randomUUID()
                .toString()
                .substring(0,6)
                .toUpperCase();
    }
}