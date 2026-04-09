package com.flightbooking.FlightInventoryService.Mapper;

import com.flightbooking.FlightInventoryService.DTO.SeatResponseDTO;
import com.flightbooking.FlightInventoryService.Entity.Seat;

public class SeatMapper {

    public static SeatResponseDTO toDTO(Seat seat) {
        return SeatResponseDTO.builder()
                .seatId(seat.getSeatId())
                .seatNumber(seat.getSeatNumber())
                .seatType(seat.getSeatType())
                .seatStatus(seat.getSeatStatus())
                .price(seat.getPrice())
                .finalPrice(seat.getFinalPrice())
                .scheduleId(seat.getSchedule().getScheduleId())
                .build();
    }
}