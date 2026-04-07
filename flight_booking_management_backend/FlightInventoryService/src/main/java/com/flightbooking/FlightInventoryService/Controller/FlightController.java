package com.flightbooking.FlightInventoryService.Controller;


import com.flightbooking.FlightInventoryService.Entity.Flight;
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
        public Flight createFlight(@Valid @RequestBody Flight flight) {
            return flightService.saveFlight(flight);
        }
        @GetMapping("/showAllflights")
        public List<Flight> getAllFlights(){
            return flightService.getAllFlights();
        }
    @DeleteMapping("/delete/{id}")
    public String deleteFlight(@PathVariable Long id) {
        flightService.deleteFlight(id);
        return "Flight cancelled successfully";
    }


    }
