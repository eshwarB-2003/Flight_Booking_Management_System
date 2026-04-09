package com.flightbooking.FlightInventoryService.Service;

import com.flightbooking.FlightInventoryService.DTO.SeatResponseDTO;
import com.flightbooking.FlightInventoryService.Entity.*;
import com.flightbooking.FlightInventoryService.Mapper.SeatMapper;
import com.flightbooking.FlightInventoryService.Repository.AircraftClassRepository;
import com.flightbooking.FlightInventoryService.Repository.SeatMapRepository;
import com.flightbooking.FlightInventoryService.Repository.SeatRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SeatService {
    @Autowired
    private SeatRepository seatRepository;
    @Autowired
   private SeatMapRepository seatMapRepository;
    @Autowired
    private AircraftClassRepository aircraftClassRepository;

    public List<SeatResponseDTO> getAvailableSeats(Long scheduleId) {
        return seatRepository
                .findBySchedule_ScheduleIdAndSeatStatus(scheduleId, SeatStatus.AVAILABLE)
                .stream()
                .map(SeatMapper::toDTO)
                .toList();
    }
    @Transactional
    public SeatResponseDTO bookSeat(Long scheduleId, String seatNumber) {

        Seat seat = seatRepository.findSeatForUpdate(scheduleId, seatNumber)
                .orElseThrow(() -> new RuntimeException("Seat Not Found"));
        if (seat.getSeatStatus() != SeatStatus.LOCKED) {
            throw new RuntimeException("Seat must be locked before booking");
        }
        AircraftClass cls = seat.getAircraftClass();
        Flight flight = seat.getSchedule().getFlight();
        double basePrice = flight.getBasePrice();
        double finalPrice = basePrice * cls.getPriceMultiplier();
        seat.setPrice(basePrice);
        seat.setFinalPrice(finalPrice);
        seat.setSeatStatus(SeatStatus.OCCUPIED);
        seat.setLockTime(null);
        return SeatMapper.toDTO(seatRepository.save(seat));
    }
    @Transactional
    public SeatResponseDTO lockSeat(Long scheduleId, String seatNumber) {

        Seat seat = seatRepository.findSeatForUpdate(scheduleId, seatNumber)
                .orElseThrow(() -> new RuntimeException("Seat Not Found"));

        if (seat.getSeatStatus() != SeatStatus.AVAILABLE) {
            throw new RuntimeException("Seat not available for locking");
        }

        seat.setSeatStatus(SeatStatus.LOCKED);
        seat.setLockTime(LocalDateTime.now());

        return SeatMapper.toDTO(seatRepository.save(seat));
    }

    public void releaseExpiredLocks() {

        List<Seat> lockedSeats = seatRepository.findBySeatStatus(SeatStatus.LOCKED);
        for (Seat seat : lockedSeats) {
            if (seat.getSeatStatus() == SeatStatus.LOCKED &&
                    seat.getLockTime() != null &&
                    seat.getLockTime().plusMinutes(5).isBefore(LocalDateTime.now())) {

                seat.setSeatStatus(SeatStatus.AVAILABLE);
                seat.setLockTime(null);

                seatRepository.save(seat);
            }
        }
    }


    public void generateSeats(FlightSchedule flightSchedule) {

        Aircraft aircraft = flightSchedule.getAircraft();
        SeatMap seatMap = aircraft.getSeatMap();
        int cols = seatMap.getCols();
        List<AircraftClass> classes =
                aircraftClassRepository.findByAircraft_AircraftId(aircraft.getAircraftId());
        // Sort classes if needed (Business first, Economy next)
        int totalClassSeats = classes.stream()
                .mapToInt(AircraftClass::getSeatCount)
                .sum();

        if (totalClassSeats != aircraft.getTotalCapacity()) {
            throw new RuntimeException("Seat count mismatch with aircraft capacity");
        }
        classes.sort((a, b) -> {
            if (a.getClassName().equals("BUSINESS")) return -1;
            if (b.getClassName().equals("BUSINESS")) return 1;
            return 0;
        });
        List<Seat> seats = new ArrayList<>();

        int currentRow = 1;

        for (AircraftClass cls : classes) {

            int totalSeats = cls.getSeatCount();

            int requiredRows = (int) Math.ceil((double) totalSeats / cols);

            for (int r = 0; r < requiredRows; r++) {

                for (int c = 0; c < cols; c++) {

                    if (totalSeats <= 0) break;

                    char columnLetter = (char) ('A' + c);
                    String seatNumber = currentRow + String.valueOf(columnLetter);

                    Seat seat = new Seat();
                    seat.setSeatNumber(seatNumber);
                    seat.setSeatStatus(SeatStatus.AVAILABLE);
                    seat.setSeatType(cls.getClassName());

                    //  Link class
                    seat.setAircraftClass(cls);

                    // Link schedule
                    seat.setSchedule(flightSchedule);

                    seats.add(seat);

                    totalSeats--;
                }

                currentRow++;
            }
        }
        seatRepository.saveAll(seats);
// logic changed for generation below but just kept for reference
       /* for (int row = 1; row <= rows; row++) {
            for (int col = 0; col < cols; col++) {

                char columnLetter = (char) ('A' + col);
                String seatNumber = row + String.valueOf(columnLetter);

                Seat seat = new Seat();
                seat.setSeatNumber(seatNumber);
                seat.setSeatStatus(SeatStatus.AVAILABLE);
                seat.setSeatType("ECONOMY");

                seat.setSchedule(flightSchedule);

                seats.add(seat);
            }
        }

        seatRepository.saveAll(seats);
        */
    }
    @Scheduled(fixedRate = 60000)
    public void autoRelease() {
        releaseExpiredLocks();
    }
}
