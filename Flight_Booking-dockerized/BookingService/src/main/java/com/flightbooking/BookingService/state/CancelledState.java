package com.flightbooking.BookingService.state;

import com.flightbooking.BookingService.entity.Booking;

public class CancelledState implements BookingState {

    @Override
    public void confirm(Booking booking) {
        throw new RuntimeException("Cancelled booking cannot confirm");
    }

    @Override
    public void cancel(Booking booking) {
        throw new RuntimeException("Already cancelled");
    }
}