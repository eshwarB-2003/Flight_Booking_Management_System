package com.flightbooking.BookingService.state;

import com.flightbooking.BookingService.entity.Booking;

public interface BookingState {

    void confirm(Booking booking);

    void cancel(Booking booking);
}