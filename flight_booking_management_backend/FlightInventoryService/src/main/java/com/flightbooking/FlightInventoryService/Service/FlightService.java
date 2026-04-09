package com.flightbooking.FlightInventoryService.Service;


import com.flightbooking.FlightInventoryService.DTO.FlightRequestDTO;
import com.flightbooking.FlightInventoryService.DTO.FlightResponseDTO;
import com.flightbooking.FlightInventoryService.Entity.Flight;
import com.flightbooking.FlightInventoryService.Entity.FlightStatus;
import com.flightbooking.FlightInventoryService.Mapper.FlightMapper;
import com.flightbooking.FlightInventoryService.Repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightService {

    @Autowired
    private FlightRepository flightRepository;

    public FlightResponseDTO saveFlight(FlightRequestDTO dto) {
        Flight flight = FlightMapper.toEntity(dto);

        if (flight.getStatus() == null) {
            flight.setStatus(FlightStatus.SCHEDULED);
        }

        Flight saved = flightRepository.save(flight);

        return FlightMapper.toDTO(saved);
    }
    public List<FlightResponseDTO> getAllFlights() {
        return flightRepository.findByStatusIn(
                List.of(FlightStatus.SCHEDULED, FlightStatus.ACTIVE)
        ).stream().map(FlightMapper::toDTO).toList();
    }
    public void deleteFlight(Long id) {

        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flight not found"));

        flight.setStatus(FlightStatus.CANCELLED);

        flightRepository.save(flight);
    }
}