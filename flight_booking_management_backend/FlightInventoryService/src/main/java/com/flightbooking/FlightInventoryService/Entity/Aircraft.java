package com.flightbooking.FlightInventoryService.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "aircraft")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Aircraft {
    @Id
    @NotBlank
    private String aircraftId;

    @Column(nullable = false)
    @NotBlank
    private String model;

    @Column(nullable = false)
    @NotBlank
    private String manufacturer;

    @NotNull
    @Min(1)
    @Column(nullable = false)
    private int totalCapacity;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @JsonManagedReference
    @OneToOne(mappedBy = "aircraft", cascade = CascadeType.ALL, orphanRemoval = true)
    private SeatMap seatMap;
    @JsonManagedReference
    @OneToMany(mappedBy = "aircraft", cascade = CascadeType.ALL)
    private List<AircraftClass> classes = new ArrayList<>();

    public void addClass(AircraftClass cls) {
        cls.setAircraft(this);
        this.classes.add(cls);
    }

    @PrePersist
    public void beforeSave() {
        if (this.aircraftId == null) {
            this.aircraftId = UUID.randomUUID().toString();
        }
        this.createdAt = LocalDateTime.now();
    }
}
