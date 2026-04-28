package com.flightbooking.FlightInventoryService.Repository;

import com.flightbooking.FlightInventoryService.Entity.FlightSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightScheduleRepository extends JpaRepository<FlightSchedule, Long> {
    boolean existsByAircraft_AircraftId(String aircraftId);
}
