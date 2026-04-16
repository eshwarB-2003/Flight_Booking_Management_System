package com.flightbooking.FlightInventoryService.DTO;

import com.flightbooking.FlightInventoryService.Entity.FlightStatus;
import lombok.Data;

@Data
public class FlightResponseDTO {

    private Long flightId;
    private String flightNumber;
    private String airline;
    private String departureCity;
    private String departureDestination;
    private FlightStatus status;
    private Double basePrice;

    public Long getFlightId() {
		return flightId;
	}

	public void setFlightId(Long flightId) {
		this.flightId = flightId;
	}

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

	public FlightStatus getStatus() {
		return status;
	}

	public void setStatus(FlightStatus status) {
		this.status = status;
	}

	public Double getBasePrice() {
		return basePrice;
	}

	public void setBasePrice(Double basePrice) {
		this.basePrice = basePrice;
	}

	private FlightResponseDTO(Builder builder) {
        this.flightId = builder.flightId;
        this.flightNumber = builder.flightNumber;
        this.airline = builder.airline;
        this.departureCity = builder.departureCity;
        this.departureDestination = builder.departureDestination;
        this.status = builder.status;
        this.basePrice = builder.basePrice;
    }

    public static class Builder {
        private Long flightId;
        private String flightNumber;
        private String airline;
        private String departureCity;
        private String departureDestination;
        private FlightStatus status;
        private Double basePrice;

        public Builder flightId(Long flightId) {
            this.flightId = flightId;
            return this;
        }

        public Builder flightNumber(String flightNumber) {
            this.flightNumber = flightNumber;
            return this;
        }

        public Builder airline(String airline) {
            this.airline = airline;
            return this;
        }

        public Builder departureCity(String departureCity) {
            this.departureCity = departureCity;
            return this;
        }

        public Builder departureDestination(String departureDestination) {
            this.departureDestination = departureDestination;
            return this;
        }

        public Builder status(FlightStatus status) {
            this.status = status;
            return this;
        }

        public Builder basePrice(Double basePrice) {
            this.basePrice = basePrice;
            return this;
        }

        public FlightResponseDTO build() {
            return new FlightResponseDTO(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}