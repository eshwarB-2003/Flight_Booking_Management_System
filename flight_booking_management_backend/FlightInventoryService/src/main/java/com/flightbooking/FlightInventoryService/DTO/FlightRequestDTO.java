package com.flightbooking.FlightInventoryService.DTO;

import com.flightbooking.FlightInventoryService.Entity.FlightStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FlightRequestDTO {

    @NotBlank
    private String flightNumber;

    @NotBlank
    private String airline;

    @NotBlank
    private String departureCity;

    @NotBlank
    private String departureDestination;

    @NotNull
    private Double basePrice;

    private FlightStatus status; // optional (can default in service)

	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	public String getAirline() {
		return airline;
	}

	public void setAirline(String airline) {
		this.airline = airline;
	}

	public String getDepartureCity() {
		return departureCity;
	}

	public void setDepartureCity(String departureCity) {
		this.departureCity = departureCity;
	}

	public String getDepartureDestination() {
		return departureDestination;
	}

	public void setDepartureDestination(String departureDestination) {
		this.departureDestination = departureDestination;
	}

	public Double getBasePrice() {
		return basePrice;
	}

	public void setBasePrice(Double basePrice) {
		this.basePrice = basePrice;
	}

	public FlightStatus getStatus() {
		return status;
	}

	public void setStatus(FlightStatus status) {
		this.status = status;
	}
    
    
}
