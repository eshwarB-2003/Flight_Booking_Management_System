package com.flightbooking.BookingService.dto;

import lombok.Data;

@Data
public class CreateBookingRequest {

    private Long userId;

    private Long flightId;

    private Long scheduleId;

    private String seatNumber;

    private Double totalAmount;
}