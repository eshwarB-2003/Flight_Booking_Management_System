package com.flightbooking.FlightInventoryService.Repository;

import com.flightbooking.FlightInventoryService.Entity.FlightSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
WHERE s.flight.departureCity = :from
AND s.flight.departureDestination = :to
AND DATE(s.departureTime) = :date
""")
    List<FlightSchedule> findByRouteAndDate(
            @Param("from") String from,
            @Param("to") String to,
            @Param("date") LocalDate date
    );
    }
