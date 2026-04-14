package com.flightbooking.FlightInventoryService.Repository;

import com.flightbooking.FlightInventoryService.Entity.Seat;
import com.flightbooking.FlightInventoryService.Entity.SeatStatus;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {

    // get available seats for a schedule
    List<Seat> findBySchedule_ScheduleIdAndSeatStatus(Long scheduleId, SeatStatus seatStatus);

    // find specific seat in a schedule
    Optional<Seat> findBySchedule_ScheduleIdAndSeatNumber(Long scheduleId, String seatNumber);

    List<Seat> findBySchedule_ScheduleId(Long scheduleId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
SELECT s FROM Seat s
WHERE s.schedule.scheduleId = :scheduleId
AND s.seatNumber = :seatNumber
""")
    Optional<Seat> findSeatForUpdate(Long scheduleId, String seatNumber);
    void deleteBySchedule_ScheduleId(Long scheduleId);
    boolean existsBySchedule_ScheduleIdAndSeatStatus(
            Long scheduleId,
            SeatStatus seatStatus
    );
    List<Seat> findBySeatStatus(SeatStatus seatStatus);
}
