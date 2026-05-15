package com.flightbooking.FlightInventoryService.Repository;

import com.flightbooking.FlightInventoryService.Entity.Flight;
import com.flightbooking.FlightInventoryService.Entity.FlightStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
    public interface FlightRepository extends JpaRepository<Flight,Long> {
    //Optional<Flight> findByFlightNumber(String flightNumber);
    List<Flight> findByStatusIn(List<FlightStatus> statuses);
}
