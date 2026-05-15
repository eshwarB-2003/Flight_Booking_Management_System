package com.flightbooking.FlightInventoryService.Mapper;

import com.flightbooking.FlightInventoryService.Entity.Aircraft;
import com.flightbooking.FlightInventoryService.Entity.Flight;
import com.flightbooking.FlightInventoryService.Entity.FlightSchedule;
import com.flightbooking.FlightInventoryService.Entity.ScheduleStatus;
import com.flightbooking.FlightInventoryService.DTO.FlightScheduleRequestDTO;
import com.flightbooking.FlightInventoryService.DTO.FlightScheduleResponseDTO;

public class FlightScheduleMapper {

    public static FlightSchedule toEntity(
            FlightScheduleRequestDTO dto,
            Flight flight,
            Aircraft aircraft
    ) {
        return FlightSchedule.builder()
                .flight(flight)
                .aircraft(aircraft)
                .departureTime(dto.getDepartureTime())
                .arrivalTime(dto.getArrivalTime())
                .departureDate(dto.getDepartureTime().toLocalDate())
                .arrivalDate(dto.getArrivalTime().toLocalDate())
                .availableSeats(aircraft.getTotalCapacity())
                .status(ScheduleStatus.SCHEDULED)
                .build();
    }

    public static FlightScheduleResponseDTO toDTO(FlightSchedule schedule) {
        return FlightScheduleResponseDTO.builder()
                .scheduleId(schedule.getScheduleId())
                .flightId(schedule.getFlight().getFlightId())
                .aircraftId(schedule.getAircraft().getAircraftId())
                .flightNumber(schedule.getFlight().getFlightNumber())
                .airline(schedule.getFlight().getAirline())
                .departureCity(schedule.getFlight().getDepartureCity())
                .destination(schedule.getFlight().getDepartureDestination())
                .departureTime(schedule.getDepartureTime())
                .arrivalTime(schedule.getArrivalTime())
                .availableSeats(schedule.getAvailableSeats())
                .status(schedule.getStatus().name())
                .build();
    }
}
