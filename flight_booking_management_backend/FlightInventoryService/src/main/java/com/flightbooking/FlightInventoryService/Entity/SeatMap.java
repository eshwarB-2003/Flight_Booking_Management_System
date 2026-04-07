package com.flightbooking.FlightInventoryService.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "seat_map")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeatMap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seatMapId;
    private int rows; // 30
    private int cols;  // 6 SO rows * cols is 180 seats


    // Link to Aircraft
    @OneToOne
    @JsonBackReference
    @JoinColumn(name = "aircraft_id")
    private Aircraft aircraft;
}