package com.flightbooking.FlightInventoryService.Controller;


import com.flightbooking.FlightInventoryService.DTO.FlightRequestDTO;
import com.flightbooking.FlightInventoryService.DTO.FlightResponseDTO;
import com.flightbooking.FlightInventoryService.Service.FlightService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
    @RequestMapping("/api/flights")
    public class FlightController {

        @Autowired
        private FlightService flightService;

        @PostMapping("/createAFlight")
        public FlightResponseDTO createFlight(@Valid @RequestBody FlightRequestDTO dto) {
            return flightService.saveFlight(dto);
        }
        @GetMapping("/showAllflights")
        public List<FlightResponseDTO> getAllFlights(){
            return flightService.getAllFlights();
        }
    @DeleteMapping("/delete/{id}")
    public String deleteFlight(@PathVariable Long id) {
        flightService.deleteFlight(id);
        return "Flight cancelled successfully";
    }


    }
