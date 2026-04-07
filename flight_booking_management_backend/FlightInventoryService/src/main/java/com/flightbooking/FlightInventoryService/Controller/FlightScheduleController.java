package com.flightbooking.FlightInventoryService.Controller;

import com.flightbooking.FlightInventoryService.Entity.FlightSchedule;
import com.flightbooking.FlightInventoryService.Service.FlightScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/schedules")
public class FlightScheduleController {

    @Autowired
    private FlightScheduleService service;

    @PostMapping
    public FlightSchedule create(@RequestBody FlightSchedule request) {
        return service.createSchedule(request);
    }
    @PutMapping("/{id}")
    public FlightSchedule updateSchedule(
            @PathVariable Long id,
            @RequestBody FlightSchedule request) {

        return service.updateSchedule(id, request);
    }
    @GetMapping("/showAll")
    public List<FlightSchedule> getAllSchedules() {
        return service.getAllSchedules();
    }
}
