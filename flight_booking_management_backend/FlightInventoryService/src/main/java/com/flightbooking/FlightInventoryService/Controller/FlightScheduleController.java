package com.flightbooking.FlightInventoryService.Controller;

import com.flightbooking.FlightInventoryService.DTO.FlightScheduleRequestDTO;
import com.flightbooking.FlightInventoryService.DTO.FlightScheduleResponseDTO;
import com.flightbooking.FlightInventoryService.Service.FlightScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/schedules")
public class FlightScheduleController {

    @Autowired
    private FlightScheduleService service;

    @PostMapping("/createSchedule")
    public FlightScheduleResponseDTO create(@RequestBody FlightScheduleRequestDTO dto) {
        return service.createSchedule(dto);
    }
    @PutMapping("/updateSchedule/{id}")
    public FlightScheduleResponseDTO updateSchedule(
            @PathVariable Long id,
            @RequestBody FlightScheduleRequestDTO dto) {

        return service.updateSchedule(id, dto);
    }
    @GetMapping("/showAll")
    public List<FlightScheduleResponseDTO> getAllSchedules() {
        return service.getAllSchedules();
    }
}
