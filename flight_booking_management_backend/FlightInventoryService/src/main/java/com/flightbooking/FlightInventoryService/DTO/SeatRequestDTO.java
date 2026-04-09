package com.flightbooking.FlightInventoryService.DTO;
import lombok.Data;

@Data
public class SeatRequestDTO {

    private Long scheduleId;
    private String seatNumber;
}
