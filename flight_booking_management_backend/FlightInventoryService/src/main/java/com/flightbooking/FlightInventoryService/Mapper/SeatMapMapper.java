package com.flightbooking.FlightInventoryService.Mapper;

import com.flightbooking.FlightInventoryService.DTO.SeatMapResponseDTO;
import com.flightbooking.FlightInventoryService.Entity.SeatMap;

    public class SeatMapMapper {

        public static SeatMapResponseDTO toDTO(SeatMap seatMap) {
            return SeatMapResponseDTO.builder()
                    .seatMapId(seatMap.getSeatMapId())
                    .rows(seatMap.getRows())
                    .cols(seatMap.getCols())
                    .aircraftId(seatMap.getAircraft().getAircraftId())
                    .build();
        }
    }