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

	public Long getClassId() {
		return classId;
	}

	public void setClassId(Long classId) {
		this.classId = classId;
	}

	public Aircraft getAircraft() {
		return aircraft;
	}

	public void setAircraft(Aircraft aircraft) {
		this.aircraft = aircraft;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Integer getSeatCount() {
		return seatCount;
	}

	public void setSeatCount(Integer seatCount) {
		this.seatCount = seatCount;
	}

	public Double getPriceMultiplier() {
		return priceMultiplier;
	}

	public void setPriceMultiplier(Double priceMultiplier) {
		this.priceMultiplier = priceMultiplier;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
    
    


}


