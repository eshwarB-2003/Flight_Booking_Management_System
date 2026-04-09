package com.flightbooking.FlightInventoryService.DTO;


import com.flightbooking.FlightInventoryService.Entity.FlightStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FlightResponseDTO {

    private Long flightId;
    private String flightNumber;
    private String airline;
    private String departureCity;
    private String departureDestination;
    private FlightStatus status;
    private Double basePrice;
}
