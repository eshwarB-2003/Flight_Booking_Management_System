package com.flightbooking.FlightInventoryService.Service;


import com.flightbooking.FlightInventoryService.Entity.Flight;
import com.flightbooking.FlightInventoryService.Entity.FlightStatus;
import com.flightbooking.FlightInventoryService.Repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightService {

    @Autowired
    private FlightRepository flightRepository;

    public Flight saveFlight(Flight flight) {
        return flightRepository.save(flight);
    }
    public List<Flight> getAllFlights() {
        return flightRepository.findByStatusIn(
                List.of(FlightStatus.SCHEDULED, FlightStatus.ACTIVE)
        );
    }
    public void deleteFlight(Long id) {

        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flight not found"));

        flight.setStatus(FlightStatus.CANCELLED);

        flightRepository.save(flight);
    }
}