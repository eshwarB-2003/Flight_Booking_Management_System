package com.flightbooking.BookingService.state;

import com.flightbooking.BookingService.entity.Booking;

public class RequestedState implements BookingState {

    @Override
    public void confirm(Booking booking) {
        booking.setBookingStatus("CONFIRMED");
    }

    @Override
    public void cancel(Booking booking) {
        booking.setBookingStatus("CANCELLED");
    }
}