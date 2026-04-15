package com.flightbooking.BookingService.state;

import com.flightbooking.BookingService.entity.Booking;

public class ConfirmedState implements BookingState {

    @Override
    public void confirm(Booking booking) {
        throw new RuntimeException("Already confirmed");
    }

    @Override
    public void cancel(Booking booking) {
        throw new RuntimeException("Cannot cancel confirmed booking");
    }
}