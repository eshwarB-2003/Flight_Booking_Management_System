package com.flightbooking.FlightInventoryService.Controller;

import com.flightbooking.FlightInventoryService.DTO.SeatResponseDTO;
import com.flightbooking.FlightInventoryService.Service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/seats")
public class SeatController {

    @Autowired
    private SeatService seatService;
    // get aall available seats for a schedule
    @GetMapping("/available/{scheduleId}")
    public List<SeatResponseDTO> getAvailableSeats(@PathVariable Long scheduleId) {
        return seatService.getAvailableSeats(scheduleId);
    }
    @PostMapping("/{scheduleId}/book")
    public SeatResponseDTO bookSeat(@PathVariable Long scheduleId,
                         @RequestParam String seatNumber) {

        return seatService.bookSeat(scheduleId, seatNumber);
    }
    @PostMapping("{scheduleId}/lock")
    public SeatResponseDTO lockSeat(@PathVariable Long scheduleId,
                         @RequestParam String seatNumber) {
        return seatService.lockSeat(scheduleId, seatNumber);
    }
    @GetMapping("/{scheduleId}")
    public List<SeatResponseDTO> getAllSeats(@PathVariable Long scheduleId) {
        return seatService.getAllSeats(scheduleId);
    }

}
