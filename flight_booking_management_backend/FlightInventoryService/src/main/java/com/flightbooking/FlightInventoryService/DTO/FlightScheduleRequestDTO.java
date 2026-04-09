package com.flightbooking.FlightInventoryService.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FlightScheduleRequestDTO {

    private Long flightId;
    private String aircraftId;

    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
}