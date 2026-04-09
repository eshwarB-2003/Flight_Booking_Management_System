package com.flightbooking.FlightInventoryService.DTO;

import com.flightbooking.FlightInventoryService.Entity.FlightStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FlightRequestDTO {

    @NotBlank
    private String flightNumber;

    @NotBlank
    private String airline;

    @NotBlank
    private String departureCity;

    @NotBlank
    private String departureDestination;

    @NotNull
    private Double basePrice;

    private FlightStatus status; // optional (can default in service)
}