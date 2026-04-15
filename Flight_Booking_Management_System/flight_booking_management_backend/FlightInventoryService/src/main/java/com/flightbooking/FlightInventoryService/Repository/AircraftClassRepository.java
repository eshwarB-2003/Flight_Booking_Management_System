package com.flightbooking.FlightInventoryService.Repository;

import com.flightbooking.FlightInventoryService.Entity.AircraftClass;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AircraftClassRepository extends JpaRepository<AircraftClass, Long> {

        List<AircraftClass> findByAircraft_AircraftId(String aircraftId);
    }
