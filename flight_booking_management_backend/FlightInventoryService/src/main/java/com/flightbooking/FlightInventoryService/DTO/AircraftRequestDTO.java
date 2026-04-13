package com.flightbooking.FlightInventoryService.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class AircraftRequestDTO {

    private String idType;
    @NotBlank
    private String model;

    @NotBlank
    private String manufacturer;

    @NotNull
    @Min(1)
    private int totalCapacity;
@NotNull
    private List<AircraftClassDTO> classes;
}