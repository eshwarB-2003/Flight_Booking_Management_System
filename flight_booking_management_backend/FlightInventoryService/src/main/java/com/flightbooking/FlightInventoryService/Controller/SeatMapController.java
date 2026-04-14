package com.flightbooking.FlightInventoryService.Controller;

import com.flightbooking.FlightInventoryService.DTO.SeatMapRequestDTO;
import com.flightbooking.FlightInventoryService.DTO.SeatMapResponseDTO;
import com.flightbooking.FlightInventoryService.Service.SeatMapService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/seatmap")
public class SeatMapController {
    @Autowired
    private SeatMapService seatMapService;

    @PostMapping
    public SeatMapResponseDTO createSeatMap(@RequestBody SeatMapRequestDTO request) {
        return seatMapService.createSeatMap(
                request.getAircraftId(),
                request.getRows(),
                request.getCols()
        );
    }

    @GetMapping("/{aircraftId}")
    public SeatMapResponseDTO getAvailableSeats(@PathVariable String aircraftId) {
        return seatMapService.getSeatMap(aircraftId);
    }
}
