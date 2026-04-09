package com.flightbooking.FlightInventoryService.DTO;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class AircraftResponseDTO {

    private String aircraftId;
    private String model;
    private String manufacturer;
    private int totalCapacity;
    private LocalDateTime createdAt;

    private List<AircraftClassDTO> classes;

    private int rows;
    private int cols;
}