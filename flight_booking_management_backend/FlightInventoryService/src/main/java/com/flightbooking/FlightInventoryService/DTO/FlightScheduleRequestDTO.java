package com.flightbooking.FlightInventoryService.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FlightScheduleRequestDTO {

    private Long flightId;
    private String aircraftId;

    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
	public Long getFlightId() {
		return flightId;
	}
	public void setFlightId(Long flightId) {
		this.flightId = flightId;
	}
	public String getAircraftId() {
		return aircraftId;
	}
	public void setAircraftId(String aircraftId) {
		this.aircraftId = aircraftId;
	}
	public LocalDateTime getDepartureTime() {
		return departureTime;
	}
	public void setDepartureTime(LocalDateTime departureTime) {
		this.departureTime = departureTime;
	}
	public LocalDateTime getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(LocalDateTime arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
}
