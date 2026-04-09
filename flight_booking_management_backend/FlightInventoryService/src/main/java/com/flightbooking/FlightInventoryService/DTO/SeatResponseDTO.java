package com.flightbooking.FlightInventoryService.DTO;

import com.flightbooking.FlightInventoryService.Entity.SeatStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SeatResponseDTO {

    private Long seatId;
    private String seatNumber;
    private String seatType;
    private SeatStatus seatStatus;

    private Double price;
    private Double finalPrice;

    private Long scheduleId;
}