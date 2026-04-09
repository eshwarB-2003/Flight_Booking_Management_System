package com.flightbooking.FlightInventoryService.DTO;


import lombok.Data;

@Data
public class SeatMapRequestDTO {

    private String aircraftId;
    private int rows;
    private int cols;
}