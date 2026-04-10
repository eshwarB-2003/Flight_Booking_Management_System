package com.flightbooking.BookingService.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookingResponse {

    private Long bookingId;

    private String bookingReference;

    private String bookingStatus;

    private Double totalAmount;
}