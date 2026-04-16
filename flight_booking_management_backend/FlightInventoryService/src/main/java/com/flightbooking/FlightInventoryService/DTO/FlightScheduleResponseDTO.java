package com.flightbooking.FlightInventoryService.DTO;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class FlightScheduleResponseDTO {

    private Long scheduleId;
    private Long flightId;
    private String aircraftId;
    private String flightNumber;
    private String airline;
    private String departureCity;
    private String destination;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private Integer availableSeats;
    private String status;

    public Long getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(Long scheduleId) {
		this.scheduleId = scheduleId;
	}

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

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
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

	public Integer getAvailableSeats() {
		return availableSeats;
	}

	public void setAvailableSeats(Integer availableSeats) {
		this.availableSeats = availableSeats;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	private FlightScheduleResponseDTO(Builder builder) {
        this.scheduleId = builder.scheduleId;
        this.flightId = builder.flightId;
        this.aircraftId = builder.aircraftId;
        this.flightNumber = builder.flightNumber;
        this.airline = builder.airline;
        this.departureCity = builder.departureCity;
        this.destination = builder.destination;
        this.departureTime = builder.departureTime;
        this.arrivalTime = builder.arrivalTime;
        this.availableSeats = builder.availableSeats;
        this.status = builder.status;
    }

    public static class Builder {
        private Long scheduleId;
        private Long flightId;
        private String aircraftId;
        private String flightNumber;
        private String airline;
        private String departureCity;
        private String destination;
        private LocalDateTime departureTime;
        private LocalDateTime arrivalTime;
        private Integer availableSeats;
        private String status;

        public Builder scheduleId(Long scheduleId) {
            this.scheduleId = scheduleId;
            return this;
        }

        public Builder flightId(Long flightId) {
            this.flightId = flightId;
            return this;
        }

        public Builder aircraftId(String aircraftId) {
            this.aircraftId = aircraftId;
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

        public Builder destination(String destination) {
            this.destination = destination;
            return this;
        }

        public Builder departureTime(LocalDateTime departureTime) {
            this.departureTime = departureTime;
            return this;
        }

        public Builder arrivalTime(LocalDateTime arrivalTime) {
            this.arrivalTime = arrivalTime;
            return this;
        }

        public Builder availableSeats(Integer availableSeats) {
            this.availableSeats = availableSeats;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public FlightScheduleResponseDTO build() {
            return new FlightScheduleResponseDTO(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}