package com.flightbooking.FlightInventoryService.Controller;

import com.flightbooking.FlightInventoryService.Entity.Aircraft;
import com.flightbooking.FlightInventoryService.Service.AircraftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/aircraft")
public class AircraftController {

    @Autowired
    private AircraftService aircraftService;

    @PostMapping(consumes = "application/json")
    public Aircraft createAircraft(@RequestBody Aircraft aircraft) {
        return aircraftService.createAircraft(aircraft);
    }

    @GetMapping("/getAircraft/{id}")
    public Aircraft getAircraft(@PathVariable String id) {
        return aircraftService.findById(id);
    }
    @PutMapping("/updateAircraft/{id}")
    public Aircraft updateAircraft(@PathVariable String id, @RequestBody Aircraft updatedAircraft) {

        return aircraftService.updateAircraft(id, updatedAircraft);
    }
    @DeleteMapping("/deleteAircraft/{id}")
    public String deleteAircraft(@PathVariable String id) {
        aircraftService.deleteAircraft(id);
        return "Aircraft deleted successfully";
    }

    @GetMapping
    public List<Aircraft> getAllAircraft() {
        return aircraftService.getAllAircrafts();
    }


}