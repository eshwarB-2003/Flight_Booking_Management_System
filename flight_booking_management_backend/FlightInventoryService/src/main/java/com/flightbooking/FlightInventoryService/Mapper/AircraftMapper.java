package com.flightbooking.FlightInventoryService.Mapper;


import com.flightbooking.FlightInventoryService.DTO.AircraftClassDTO;
import com.flightbooking.FlightInventoryService.DTO.AircraftRequestDTO;
import com.flightbooking.FlightInventoryService.DTO.AircraftResponseDTO;
import com.flightbooking.FlightInventoryService.Entity.Aircraft;
import com.flightbooking.FlightInventoryService.Entity.AircraftClass;

import java.util.stream.Collectors;

public class AircraftMapper {

    public static Aircraft toEntity(AircraftRequestDTO dto) {
        Aircraft aircraft = new Aircraft();

        aircraft.setModel(dto.getModel());
        aircraft.setManufacturer(dto.getManufacturer());
        aircraft.setTotalCapacity(dto.getTotalCapacity());

        if (dto.getClasses() != null) {
            for (AircraftClassDTO clsDTO : dto.getClasses()) {
                AircraftClass cls = toClassEntity(clsDTO);
                aircraft.addClass(cls);
            }
        }
            return aircraft;
        }

        public static AircraftResponseDTO toDTO(Aircraft aircraft) {
        return AircraftResponseDTO.builder()
                .aircraftId(aircraft.getAircraftId())
                .model(aircraft.getModel())
                .manufacturer(aircraft.getManufacturer())
                .totalCapacity(aircraft.getTotalCapacity())
                .createdAt(aircraft.getCreatedAt())
                .classes(
                        aircraft.getClasses().stream()
                                .map(AircraftMapper::toClassDTO)
                                .collect(Collectors.toList())
                )
                .rows(aircraft.getSeatMap() != null ? aircraft.getSeatMap().getRows() : 0)
                .cols(aircraft.getSeatMap() != null ? aircraft.getSeatMap().getCols() : 0)
                .build();
    }

    private static AircraftClass toClassEntity(AircraftClassDTO dto) {
        AircraftClass cls = new AircraftClass();
        cls.setClassName(dto.getClassName());
        cls.setSeatCount(dto.getSeatCount());
        cls.setPriceMultiplier(dto.getPriceMultiplier());
        cls.setStatus(dto.getStatus());
        return cls;
    }

    private static AircraftClassDTO toClassDTO(AircraftClass entity) {
        AircraftClassDTO dto = new AircraftClassDTO();
        dto.setClassName(entity.getClassName());
        dto.setSeatCount(entity.getSeatCount());
        dto.setPriceMultiplier(entity.getPriceMultiplier());
        dto.setStatus(entity.getStatus());
        return dto;
    }
}