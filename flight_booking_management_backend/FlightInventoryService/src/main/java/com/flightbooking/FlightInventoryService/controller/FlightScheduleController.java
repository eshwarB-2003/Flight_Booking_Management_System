package com.flightbooking.FlightInventoryService.Controller;

import com.flightbooking.FlightInventoryService.DTO.FlightScheduleResponseDTO;
import com.flightbooking.FlightInventoryService.Entity.FlightSchedule;
import com.flightbooking.FlightInventoryService.Service.FlightScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
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
     @GetMapping("/search")
    public List<FlightScheduleResponseDTO> searchFlights(
            @RequestParam("from") String from,
            @RequestParam("to") String to,
             @RequestParam("date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){

        return service.searchFlights(from, to, date);
    }
    @GetMapping("/showAll")
    public List<FlightSchedule> getAllSchedules() {
        return service.getAllSchedules();
    }
}
