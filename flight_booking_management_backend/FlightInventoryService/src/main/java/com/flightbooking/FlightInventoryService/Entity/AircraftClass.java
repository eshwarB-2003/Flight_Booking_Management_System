package com.flightbooking.FlightInventoryService.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "aircraft_class")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AircraftClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long classId;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "aircraft_id", nullable = false)
    private Aircraft aircraft;

    private String className;

    private Integer seatCount;

    private Double priceMultiplier;

    private String status;


}


