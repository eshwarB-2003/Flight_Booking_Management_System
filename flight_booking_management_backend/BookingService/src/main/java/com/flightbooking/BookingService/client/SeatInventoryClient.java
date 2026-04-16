package com.flightbooking.BookingService.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "flight-inventory", url = "http://localhost:8081")
public interface SeatInventoryClient {

    @PostMapping("/api/seats/{scheduleId}/lock")
    Object lockSeat(
            @PathVariable Long scheduleId,
            @RequestParam String seatNumber
    );
}