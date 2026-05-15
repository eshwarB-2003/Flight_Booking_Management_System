package com.flightbooking.FlightInventoryService.Mapper;

import com.flightbooking.FlightInventoryService.Entity.Flight;
import com.flightbooking.FlightInventoryService.DTO.FlightRequestDTO;
import com.flightbooking.FlightInventoryService.DTO.FlightResponseDTO;

public class FlightMapper {

    public static Flight toEntity(FlightRequestDTO dto) {
        return Flight.builder()
                .flightNumber(dto.getFlightNumber())
                .airline(dto.getAirline())
                .departureCity(dto.getDepartureCity())
                .departureDestination(dto.getDepartureDestination())
                .basePrice(dto.getBasePrice())
                .status(dto.getStatus() != null ? dto.getStatus() : null)
                .build();
    }

    public static FlightResponseDTO toDTO(Flight flight) {
        return FlightResponseDTO.builder()
                .flightId(flight.getFlightId())
                .flightNumber(flight.getFlightNumber())
                .airline(flight.getAirline())
                .departureCity(flight.getDepartureCity())
                .departureDestination(flight.getDepartureDestination())
                .status(flight.getStatus())
                .basePrice(flight.getBasePrice())
                .build();
    }
}
