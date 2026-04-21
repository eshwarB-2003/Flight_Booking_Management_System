package com.flightbooking.FlightInventoryService.Controller;

import com.flightbooking.FlightInventoryService.Entity.Seat;
import com.flightbooking.FlightInventoryService.Service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/seats")
public class SeatController {

    @Autowired
    private SeatService seatService;

    @GetMapping("/available/{scheduleId}")
    public List<Seat> getAvailableSeats(@PathVariable ("scheduleId")  Long scheduleId) {
        return seatService.getAvailableSeats(scheduleId);
    }

  @PostMapping("/{scheduleId}/book")
public Seat bookSeat(@PathVariable("scheduleId") Long scheduleId,
                     @RequestParam("seatNumber") String seatNumber) {

    return seatService.bookSeat(scheduleId, seatNumber);
}
   @PostMapping("/{scheduleId}/lock")
public Seat lockSeat(@PathVariable("scheduleId") Long scheduleId,
                     @RequestParam("seatNumber") String seatNumber) {

    return seatService.lockSeat(scheduleId, seatNumber);
}
}
