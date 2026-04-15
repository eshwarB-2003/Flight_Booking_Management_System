package com.flightbooking.FlightInventoryService.Service;

import com.flightbooking.FlightInventoryService.Entity.Aircraft;
import com.flightbooking.FlightInventoryService.Entity.AircraftClass;
import com.flightbooking.FlightInventoryService.Entity.SeatMap;
import com.flightbooking.FlightInventoryService.Repository.AircraftRepository;
import com.flightbooking.FlightInventoryService.Repository.FlightScheduleRepository;
import com.flightbooking.FlightInventoryService.Repository.SeatMapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
    @RequiredArgsConstructor
    public class AircraftService {
    @Autowired
    private AircraftRepository aircraftRepository;

    @Autowired
    private SeatMapRepository seatMapRepository;

    @Autowired
    private FlightScheduleRepository flightScheduleRepository;

    // creating an aircraft
    public Aircraft createAircraft(Aircraft aircraft) {

        List<AircraftClass> inputClasses = aircraft.getClasses();
        if (inputClasses != null) {
            int totalSeats = inputClasses.stream()
                    .mapToInt(AircraftClass::getSeatCount)
                    .sum();

            if (totalSeats != aircraft.getTotalCapacity()) {
                throw new RuntimeException(
                        "Seat count (" + totalSeats + ") must match aircraft capacity ("
                                + aircraft.getTotalCapacity() + ")"
                );
            }
        }

        aircraft.setClasses(new ArrayList<>());

        if (inputClasses != null) {
            for (AircraftClass cls : inputClasses) {
                aircraft.addClass(cls);
            }
        }
        Aircraft savedAircraft = aircraftRepository.save(aircraft);
        Optional<SeatMap> existingSeatMap =
                seatMapRepository.findByAircraft_AircraftId(savedAircraft.getAircraftId());

        if (existingSeatMap.isEmpty()) {
            SeatMap seatMap = new SeatMap();
            seatMap.setAircraft(savedAircraft);
            seatMap.setRows(savedAircraft.getTotalCapacity() / 6);
            seatMap.setCols(6);

            seatMapRepository.save(seatMap);
            savedAircraft.setSeatMap(seatMap);
        }

        return savedAircraft;
    }

    // finding by id
    public Aircraft findById(String id) {
        return aircraftRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aircraft not found"));
    }

     // updation of aircraft
    public Aircraft updateAircraft(String id, Aircraft updated) {

        Aircraft aircraft = aircraftRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aircraft not found"));

        boolean hasSchedules = flightScheduleRepository
                .existsByAircraft_AircraftId(id);

        if (hasSchedules) {
            throw new RuntimeException("Cannot update aircraft after schedules are created");
        }

        aircraft.setModel(updated.getModel());
        aircraft.setManufacturer(updated.getManufacturer());
        aircraft.setTotalCapacity(updated.getTotalCapacity());

        SeatMap seatMap = aircraft.getSeatMap();
        if (seatMap != null) {
            seatMap.setRows((int) Math.ceil(updated.getTotalCapacity() / 6.0));
            seatMap.setCols(6);
        }

        return aircraftRepository.save(aircraft);
    }

   // delete
    public void deleteAircraft(String id) {

        Aircraft aircraft = aircraftRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aircraft not found"));

        boolean hasSchedules = flightScheduleRepository
                .existsByAircraft_AircraftId(id);

        if (hasSchedules) {
            throw new RuntimeException("Cannot delete aircraft with active schedules");
        }

        aircraftRepository.delete(aircraft);
    }
    // get all aircrafts
    public List<Aircraft> getAllAircrafts() {
        return aircraftRepository.findAll();
    }
}
