package com.flightbooking.FlightInventoryService.Service;

import com.flightbooking.FlightInventoryService.DTO.FlightScheduleRequestDTO;
import com.flightbooking.FlightInventoryService.DTO.FlightScheduleResponseDTO;
import com.flightbooking.FlightInventoryService.Entity.Aircraft;
import com.flightbooking.FlightInventoryService.Entity.Flight;
import com.flightbooking.FlightInventoryService.Entity.FlightSchedule;
import com.flightbooking.FlightInventoryService.Mapper.FlightScheduleMapper;
import com.flightbooking.FlightInventoryService.Repository.AircraftRepository;
import com.flightbooking.FlightInventoryService.Repository.FlightRepository;
import com.flightbooking.FlightInventoryService.Repository.FlightScheduleRepository;
import com.flightbooking.FlightInventoryService.Repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

   // public FlightSchedule createSchedule(FlightSchedule request) {
        public FlightScheduleResponseDTO createSchedule(FlightScheduleRequestDTO dto) {

                Flight flight = flightRepository.findById(dto.getFlightId())
                        .orElseThrow(() -> new RuntimeException("Flight not found"));

                Aircraft aircraft = aircraftRepository.findById(dto.getAircraftId())
                        .orElseThrow(() -> new RuntimeException("Aircraft not found"));
            validateSchedule(dto);
            boolean conflict = scheduleRepository.existsOverlappingSchedule(
                    dto.getAircraftId(),
                    dto.getDepartureTime(),
                    dto.getArrivalTime()
            );


                FlightSchedule schedule = FlightScheduleMapper.toEntity(dto, flight, aircraft);

                FlightSchedule saved = scheduleRepository.save(schedule);

                seatService.generateSeats(saved);

                return FlightScheduleMapper.toDTO(saved);
            }
/*
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
    */


    // schedule updation ,
    /* Guys Need of Delete Schedule is not needed as i thought possibilities of that would be less */
    public FlightScheduleResponseDTO updateSchedule(Long id, FlightScheduleRequestDTO dto) {

        FlightSchedule existing = scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));
        if (dto.getDepartureTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Cannot schedule in the past");
        }
        validateSchedule(dto);
        // Update timings
        existing.setDepartureTime(dto.getDepartureTime());
        existing.setArrivalTime(dto.getArrivalTime());

        existing.setDepartureDate(dto.getDepartureTime().toLocalDate());
        existing.setArrivalDate(dto.getArrivalTime().toLocalDate());
        if (dto.getAircraftId() != null) {

            Aircraft aircraft = aircraftRepository.findById(dto.getAircraftId())
                    .orElseThrow(() -> new RuntimeException("Aircraft not found"));
            existing.setAircraft(aircraft);

            //regenerate seats
            seatRepository.deleteBySchedule_ScheduleId(id);
            existing.setAvailableSeats(aircraft.getTotalCapacity());
            seatService.generateSeats(existing);
        }

        FlightSchedule saved = scheduleRepository.save(existing);

        return FlightScheduleMapper.toDTO(saved);
    }

    /*
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
    */
    public List<FlightScheduleResponseDTO> getAllSchedules() {
        return scheduleRepository.findAll()
                .stream()
                .map(FlightScheduleMapper::toDTO)
                .toList();
    }
    private void validateSchedule(FlightScheduleRequestDTO dto) {
        if (dto.getArrivalTime().isBefore(dto.getDepartureTime())) {
            throw new RuntimeException("Invalid schedule timing");
        }
    }
    public List<FlightScheduleResponseDTO> searchFlights(String from, String to, String date) {

        LocalDate searchDate = LocalDate.parse(date);

        List<FlightSchedule> schedules =
                scheduleRepository.findByRouteAndDate(from, to, searchDate);

        return schedules.stream()
                .map(FlightScheduleMapper::toDTO)
                .toList();
    }
}