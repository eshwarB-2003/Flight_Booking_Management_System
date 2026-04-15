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


    public Long getSeatMapId() {
		return seatMapId;
	}

	public void setSeatMapId(Long seatMapId) {
		this.seatMapId = seatMapId;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getCols() {
		return cols;
	}

	public void setCols(int cols) {
		this.cols = cols;
	}

	public Aircraft getAircraft() {
		return aircraft;
	}

	public void setAircraft(Aircraft aircraft) {
		this.aircraft = aircraft;
	}

	// Link to Aircraft
    @OneToOne
    @JsonBackReference
    @JoinColumn(name = "aircraft_id")
    private Aircraft aircraft;
}