package com.flightbooking.FlightInventoryService.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

    @Entity
    @Table(name = "flight_schedule")
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public class FlightSchedule {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long scheduleId;
        @NotNull
        private LocalDate departureDate;
        private LocalDate arrivalDate;
        private LocalDateTime departureTime;
        private LocalDateTime arrivalTime;
        @Column(nullable = false)
        private Integer availableSeats;

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        private ScheduleStatus status;

        @ManyToOne
        @JoinColumn(name = "flight_id")
        private Flight flight;
        @ManyToOne
        @JoinColumn(name = "aircraft_id")
        private Aircraft aircraft;
    }
