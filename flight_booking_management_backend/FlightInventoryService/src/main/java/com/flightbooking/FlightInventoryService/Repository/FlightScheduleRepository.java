package com.flightbooking.FlightInventoryService.Repository;

import com.flightbooking.FlightInventoryService.Entity.FlightSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface FlightScheduleRepository extends JpaRepository<FlightSchedule, Long> {
    boolean existsByAircraft_AircraftId(String aircraftId);

    @Query("""
        SELECT COUNT(s) > 0 FROM FlightSchedule s
        WHERE s.aircraft.aircraftId = :aircraftId
        AND (
            (:departure BETWEEN s.departureTime AND s.arrivalTime)
            OR
            (:arrival BETWEEN s.departureTime AND s.arrivalTime)
        )
    """)
    boolean existsOverlappingSchedule(
            String aircraftId,
            LocalDateTime departure,
            LocalDateTime arrival
    );
}
