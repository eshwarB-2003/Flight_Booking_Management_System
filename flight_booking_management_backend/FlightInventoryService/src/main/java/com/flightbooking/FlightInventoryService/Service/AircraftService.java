package com.flightbooking.FlightInventoryService.Service;

import com.flightbooking.FlightInventoryService.DTO.AircraftClassDTO;
import com.flightbooking.FlightInventoryService.DTO.AircraftRequestDTO;
import com.flightbooking.FlightInventoryService.DTO.AircraftResponseDTO;
import com.flightbooking.FlightInventoryService.Entity.Aircraft;
import com.flightbooking.FlightInventoryService.Entity.AircraftClass;
import com.flightbooking.FlightInventoryService.Entity.SeatMap;
import com.flightbooking.FlightInventoryService.Factory.AircraftIdFactory;
import com.flightbooking.FlightInventoryService.Mapper.AircraftMapper;
import com.flightbooking.FlightInventoryService.Repository.AircraftRepository;
import com.flightbooking.FlightInventoryService.Repository.FlightScheduleRepository;
import com.flightbooking.FlightInventoryService.Repository.SeatMapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
    @RequiredArgsConstructor
    public class AircraftService {
    @Autowired
    private AircraftRepository aircraftRepository;

    @Autowired
    private SeatMapRepository seatMapRepository;

    @Autowired
    private FlightScheduleRepository flightScheduleRepository;

    @Autowired
    private AircraftIdFactory aircraftIdFactory;

    // creating an aircraft
    public AircraftResponseDTO createAircraft(AircraftRequestDTO dto) {
        Aircraft aircraft = AircraftMapper.toEntity(dto);
        String id = aircraftIdFactory
                .getGenerator(dto.getIdType())
                .generateId();
        aircraft.setAircraftId(id);
        if (dto.getClasses() != null) {
            int totalSeats = dto.getClasses().stream()
                    .mapToInt(cls -> cls.getSeatCount())
                    .sum();
            if (totalSeats != aircraft.getTotalCapacity()) {
                throw new RuntimeException(
                        "Seat count (" + totalSeats + ") must match aircraft capacity ("
                                + aircraft.getTotalCapacity() + ")"
                );
            }
        }

        // save aircraft
        Aircraft savedAircraft = aircraftRepository.save(aircraft);

        // Creation of SeatMap wil be done aautomaticallu
        Optional<SeatMap> existingSeatMap =
                seatMapRepository.findByAircraft_AircraftId(savedAircraft.getAircraftId());

        if (existingSeatMap.isEmpty()) {
            SeatMap seatMap = new SeatMap();
            seatMap.setAircraft(savedAircraft);
            seatMap.setRows((int) Math.ceil(savedAircraft.getTotalCapacity() / 6.0));
            seatMap.setCols(6);

            seatMapRepository.save(seatMap);
            savedAircraft.setSeatMap(seatMap);
        }
        return AircraftMapper.toDTO(savedAircraft);
    }

    // finding by id
    public AircraftResponseDTO  findById(String id) {
        Aircraft aircraft = aircraftRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aircraft not found"));

        return AircraftMapper.toDTO(aircraft);
    }

     // updation of aircraft
     public AircraftResponseDTO updateAircraft(String id, AircraftRequestDTO dto) {

         Aircraft aircraft = aircraftRepository.findById(id)
                 .orElseThrow(() -> new RuntimeException("Aircraft not found"));

         boolean hasSchedules = flightScheduleRepository
                 .existsByAircraft_AircraftId(id);

         if (hasSchedules) {
             throw new RuntimeException("Cannot update aircraft after schedules are created");
         }
         aircraft.setModel(dto.getModel());
         aircraft.setManufacturer(dto.getManufacturer());
         aircraft.setTotalCapacity(dto.getTotalCapacity());
         aircraft.getClasses().clear();
         aircraftRepository.save(aircraft);
         if (dto.getClasses() != null) {
             for (AircraftClassDTO clsDTO : dto.getClasses()) {

                 AircraftClass cls = new AircraftClass();
                 cls.setClassName(clsDTO.getClassName());
                 cls.setSeatCount(clsDTO.getSeatCount());
                 cls.setPriceMultiplier(clsDTO.getPriceMultiplier());
                 cls.setStatus(clsDTO.getStatus());

                 aircraft.addClass(cls); // sets relationship
             }
         }

         // Update seat map
         SeatMap seatMap = aircraft.getSeatMap();
         if (seatMap != null) {
             seatMap.setRows((int) Math.ceil(dto.getTotalCapacity() / 6.0));
             seatMap.setCols(6);
         }
         int totalSeats = dto.getClasses().stream()
                 .mapToInt(cls -> cls.getSeatCount())
                 .sum();

         if (totalSeats != dto.getTotalCapacity()) {
             throw new RuntimeException(
                     "Seat count (" + totalSeats +
                             ") must match aircraft capacity (" + dto.getTotalCapacity() + ")"
             );
         }
         Aircraft saved = aircraftRepository.save(aircraft);

         return AircraftMapper.toDTO(saved);
     }
   // delete
    public void deleteAircraft(String id) {

        Aircraft aircraft = aircraftRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aircraft not found"));

        boolean hasSchedules = flightScheduleRepository
                .existsByAircraft_AircraftId(id);

        if (hasSchedules) {
            throw new RuntimeException("Cannot delete aircraft with active schedules");
        }

        aircraftRepository.delete(aircraft);
    }
    // get all aircrafts
    public List<AircraftResponseDTO> getAllAircrafts() {
        return aircraftRepository.findAll()
                .stream()
                .map(AircraftMapper::toDTO)
                .toList();
    }
}
