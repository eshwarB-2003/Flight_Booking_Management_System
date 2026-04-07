package com.flightbooking.FlightInventoryService.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table (name = "seat")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seatId;
    @Column(nullable = false)
    private String seatNumber;
    @Column(nullable = false)
    private String seatType;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SeatStatus seatStatus;
    private Double price;
    private Double finalPrice;
    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private FlightSchedule schedule;
    @Column(name = "lock_time", nullable = true)
    private LocalDateTime lockTime;
    @ManyToOne
    @JoinColumn(name = "class_id")
    private AircraftClass aircraftClass;

}