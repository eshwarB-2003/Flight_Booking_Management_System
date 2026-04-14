package com.flightbooking.FlightInventoryService.Controller;

import com.flightbooking.FlightInventoryService.DTO.AircraftRequestDTO;
import com.flightbooking.FlightInventoryService.DTO.AircraftResponseDTO;
import com.flightbooking.FlightInventoryService.Service.AircraftService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/aircraft")
public class AircraftController {

    @Autowired
    private AircraftService aircraftService;

    @PostMapping("/createAircraft")
    public AircraftResponseDTO createAircraft(@Valid @RequestBody AircraftRequestDTO dto) {
        return aircraftService.createAircraft(dto);
    }

    @GetMapping("/getAircraft/{id}")
    public AircraftResponseDTO getAircraft(@PathVariable String id) {
        return aircraftService.findById(id);
    }
    @PutMapping("/updateAircraft/{id}")
    public AircraftResponseDTO updateAircraft(@PathVariable String id, @Valid @RequestBody AircraftRequestDTO dto) {

        return aircraftService.updateAircraft(id, dto);
    }
    @DeleteMapping("/deleteAircraft/{id}")
    public String deleteAircraft(@PathVariable String id) {
        aircraftService.deleteAircraft(id);
        return "Aircraft deleted successfully";
    }

    @GetMapping("/getAllAircrafts")
    public List<AircraftResponseDTO> getAllAircraft() {
        return aircraftService.getAllAircrafts();
    }


}