package com.flightbooking.FlightInventoryService.Controller;

import com.flightbooking.FlightInventoryService.Entity.Seat;
import com.flightbooking.FlightInventoryService.Service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seats")
public class SeatController {

    @Autowired
    private SeatService seatService;
    // get aall available seats for a schedule
    @GetMapping("/available/{scheduleId}")
    public List<Seat> getAvailableSeats(@PathVariable Long scheduleId) {
        return seatService.getAvailableSeats(scheduleId);
    }
    @PostMapping("/book")
    public Seat bookSeat(@RequestParam Long scheduleId,
                         @RequestParam String seatNumber) {

        return seatService.bookSeat(scheduleId, seatNumber);
    }
    @PostMapping("/lock")
    public Seat lockSeat(@RequestParam Long scheduleId,
                         @RequestParam String seatNumber) {
        return seatService.lockSeat(scheduleId, seatNumber);
    }

}
