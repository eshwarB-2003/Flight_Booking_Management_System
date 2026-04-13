package com.flightbooking.FlightInventoryService.DTO;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class FlightScheduleResponseDTO {

    private Long scheduleId;

    private Long flightId;
    private String aircraftId;
    private String flightNumber;
    private String airline;
    private String departureCity;
    private String destination;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private Integer availableSeats;
    private String status;
}

