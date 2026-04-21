package com.flightbooking.FlightInventoryService.Repository;

import com.flightbooking.FlightInventoryService.Entity.FlightSchedule;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

     @Query("""
SELECT s FROM FlightSchedule s
WHERE LOWER(s.flight.departureCity) = LOWER(:from)
AND LOWER(s.flight.departureDestination) = LOWER(:to)
AND s.departureTime >= :start
AND s.departureTime < :end
""")
List<FlightSchedule> searchFlights(
    @Param("from") String from,
    @Param("to") String to,
    @Param("start") LocalDateTime start,
    @Param("end") LocalDateTime end
);
    }
