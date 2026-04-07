package com.flightbooking.FlightInventoryService.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "flight")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long flightId;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String flightNumber;

    @NotBlank
    @Column(nullable = false)
    private String airline;

    @NotBlank
    @Column(nullable = false)
    private String departureCity;

    @NotBlank
    @Column(nullable = false)
    private String departureDestination;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FlightStatus status;

    @Column(nullable = false)
    private Double basePrice;

}