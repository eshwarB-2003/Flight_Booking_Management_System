package com.flightbooking.FlightInventoryService.Service;

import com.flightbooking.FlightInventoryService.Entity.Aircraft;
import com.flightbooking.FlightInventoryService.Entity.Flight;
import com.flightbooking.FlightInventoryService.Entity.FlightSchedule;
import com.flightbooking.FlightInventoryService.Entity.ScheduleStatus;
import com.flightbooking.FlightInventoryService.Repository.AircraftRepository;
import com.flightbooking.FlightInventoryService.Repository.FlightRepository;
import com.flightbooking.FlightInventoryService.Repository.FlightScheduleRepository;
import com.flightbooking.FlightInventoryService.Repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightScheduleService {
    @Autowired
    private FlightScheduleRepository scheduleRepository;
    @Autowired
    private FlightRepository flightRepository;
    @Autowired
    private AircraftRepository aircraftRepository;
    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private SeatService seatService;

    public FlightSchedule createSchedule(FlightSchedule request) {

        //  get actual Flight
        Flight flight = flightRepository.findById(
                request.getFlight().getFlightId()
        ).orElseThrow();

        // get actual Aircraft
        Aircraft aircraft = aircraftRepository.findById(
                request.getAircraft().getAircraftId()
        ).orElseThrow();

        // Setting of proper objects
        FlightSchedule schedule = new FlightSchedule();
        schedule.setFlight(flight);
        schedule.setAircraft(aircraft);
        schedule.setDepartureDate(request.getDepartureTime().toLocalDate());
        schedule.setArrivalDate(request.getArrivalTime().toLocalDate());
        schedule.setDepartureTime(request.getDepartureTime());
        schedule.setArrivalTime(request.getArrivalTime());
        int totalSeats = aircraft.getTotalCapacity();   //
        schedule.setAvailableSeats(totalSeats);         //
        schedule.setStatus(ScheduleStatus.SCHEDULED);                //

        // save schedule
        scheduleRepository.save(schedule);

        // generate seats
        seatService.generateSeats(schedule);

        return schedule;
    }
    // schedule updation ,
    /* Guys Need of Delete Schedule is not needed as i thought possibilities of that would be less */
    public FlightSchedule updateSchedule(Long id, FlightSchedule request) {

        FlightSchedule existing = scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));

        // Update timings in the schedule
        existing.setDepartureTime(request.getDepartureTime());
        existing.setArrivalTime(request.getArrivalTime());

        existing.setDepartureDate(request.getDepartureTime().toLocalDate());
        existing.setArrivalDate(request.getArrivalTime().toLocalDate());

        if (request.getAircraft() != null) {
            Aircraft aircraft = aircraftRepository.findById(
                    request.getAircraft().getAircraftId()
            ).orElseThrow(() -> new RuntimeException("Aircraft not found"));

            existing.setAircraft(aircraft);

            seatRepository.deleteBySchedule_ScheduleId(id);
            seatService.generateSeats(existing);
        }

        return scheduleRepository.save(existing);
    }
    public List<FlightSchedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }
}