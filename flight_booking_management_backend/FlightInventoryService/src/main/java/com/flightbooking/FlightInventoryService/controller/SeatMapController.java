package com.flightbooking.FlightInventoryService.controller;

import com.flightbooking.FlightInventoryService.DTO.SeatMapRequest;
import com.flightbooking.FlightInventoryService.Entity.SeatMap;
import com.flightbooking.FlightInventoryService.Service.SeatMapService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/seatmap")
public class SeatMapController {
    @Autowired
    private SeatMapService seatMapService;

    @PostMapping
    public SeatMap createSeatMap(@RequestBody SeatMapRequest request) {
        return seatMapService.createSeatMap(
                request.getAircraftId(),
                request.getRows(),
                request.getCols()
        );
    }

    @GetMapping("/{aircraftId}")
    public SeatMap getAvailableSeats(@PathVariable String aircraftId) {
        return seatMapService.getSeatMap(aircraftId);
    }
}
