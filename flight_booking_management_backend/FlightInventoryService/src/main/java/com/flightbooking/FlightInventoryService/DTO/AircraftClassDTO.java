package com.flightbooking.FlightInventoryService.DTO;



import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AircraftClassDTO {

    @NotBlank
    private String className;

    @Min(1)
    private Integer seatCount;

    private Double priceMultiplier;

    private String status;
}