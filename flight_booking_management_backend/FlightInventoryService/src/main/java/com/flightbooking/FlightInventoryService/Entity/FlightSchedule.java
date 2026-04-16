package com.flightbooking.FlightInventoryService.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "flight_schedule")
@Data
public class FlightSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;

    @NotNull
    private LocalDate departureDate;

    private LocalDate arrivalDate;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;

    @Column(nullable = false)
    private Integer availableSeats;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ScheduleStatus status;

    @ManyToOne
    @JoinColumn(name = "flight_id")
    private Flight flight;

    @ManyToOne
    @JoinColumn(name = "aircraft_id")
    private Aircraft aircraft;

   
    public Long getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(Long scheduleId) {
		this.scheduleId = scheduleId;
	}

	public LocalDate getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(LocalDate departureDate) {
		this.departureDate = departureDate;
	}

	public LocalDate getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(LocalDate arrivalDate) {
		this.arrivalDate = arrivalDate;
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

	public ScheduleStatus getStatus() {
		return status;
	}

	public void setStatus(ScheduleStatus status) {
		this.status = status;
	}

	public Flight getFlight() {
		return flight;
	}

	public void setFlight(Flight flight) {
		this.flight = flight;
	}

	public Aircraft getAircraft() {
		return aircraft;
	}

	public void setAircraft(Aircraft aircraft) {
		this.aircraft = aircraft;
	}
	
	public FlightSchedule() {
		
	}

	private FlightSchedule(Builder builder) {
        this.scheduleId = builder.scheduleId;
        this.departureDate = builder.departureDate;
        this.arrivalDate = builder.arrivalDate;
        this.departureTime = builder.departureTime;
        this.arrivalTime = builder.arrivalTime;
        this.availableSeats = builder.availableSeats;
        this.status = builder.status;
        this.flight = builder.flight;
        this.aircraft = builder.aircraft;
    }

    // Builder class
    public static class Builder {
        private Long scheduleId;
        private LocalDate departureDate;
        private LocalDate arrivalDate;
        private LocalDateTime departureTime;
        private LocalDateTime arrivalTime;
        private Integer availableSeats;
        private ScheduleStatus status;
        private Flight flight;
        private Aircraft aircraft;

        public Builder scheduleId(Long scheduleId) {
            this.scheduleId = scheduleId;
            return this;
        }

        public Builder departureDate(LocalDate departureDate) {
            this.departureDate = departureDate;
            return this;
        }

        public Builder arrivalDate(LocalDate arrivalDate) {
            this.arrivalDate = arrivalDate;
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

        public Builder status(ScheduleStatus status) {
            this.status = status;
            return this;
        }

        public Builder flight(Flight flight) {
            this.flight = flight;
            return this;
        }

        public Builder aircraft(Aircraft aircraft) {
            this.aircraft = aircraft;
            return this;
        }

        public FlightSchedule build() {
            // Optional validation
            if (availableSeats == null || availableSeats < 0) {
                throw new IllegalArgumentException("Available seats must be >= 0");
            }
            if (departureDate == null) {
                throw new IllegalArgumentException("Departure date is required");
            }
            return new FlightSchedule(this);
        }
    }

    // Entry point
    public static Builder builder() {
        return new Builder();
    }
}