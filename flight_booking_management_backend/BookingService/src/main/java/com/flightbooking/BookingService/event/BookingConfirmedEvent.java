package com.flightbooking.BookingService.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookingConfirmedEvent {

    private Long bookingId;
    private Long userId;
    private Long flightId;
}