package com.flightbooking.FlightInventoryService.Service;

import com.flightbooking.FlightInventoryService.DTO.SeatMapResponseDTO;
import com.flightbooking.FlightInventoryService.Entity.Aircraft;
import com.flightbooking.FlightInventoryService.Entity.SeatMap;
import com.flightbooking.FlightInventoryService.Mapper.SeatMapMapper;
import com.flightbooking.FlightInventoryService.Repository.AircraftRepository;
import com.flightbooking.FlightInventoryService.Repository.SeatMapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeatMapService {

    @Autowired
    private SeatMapRepository seatMapRepository;

    @Autowired
    private AircraftRepository aircraftRepository;

    public SeatMapResponseDTO createSeatMap(String aircraftId, int rows, int cols) {

        Aircraft aircraft = aircraftRepository.findById(aircraftId)
                .orElseThrow(() -> new RuntimeException("Aircraft not found"));
        if (rows * cols != aircraft.getTotalCapacity()) {
            throw new RuntimeException("SeatMap does not match aircraft capacity");
        }

        SeatMap seatMap = seatMapRepository
                .findByAircraft_AircraftId(aircraftId)
                .orElse(new SeatMap());

        seatMap.setAircraft(aircraft);
        seatMap.setRows(rows);
        seatMap.setCols(cols);

        return SeatMapMapper.toDTO(seatMapRepository.save(seatMap));
    }

    public SeatMapResponseDTO getSeatMap(String aircraftId) {
        SeatMap seatMap = seatMapRepository
                .findByAircraft_AircraftId(aircraftId)
                .orElseThrow(() -> new RuntimeException("SeatMap not found"));

        return SeatMapMapper.toDTO(seatMap);
    }
}