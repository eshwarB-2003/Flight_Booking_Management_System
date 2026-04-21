package com.flightbooking.FlightInventoryService.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "flight")
@Data
@NoArgsConstructor
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

    private Flight(Builder builder) {
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

        public Flight build() {
            if (flightNumber == null || flightNumber.isBlank()) {
                throw new IllegalArgumentException("Flight number is required");
            }
            if (basePrice == null || basePrice < 0) {
                throw new IllegalArgumentException("Base price must be >= 0");
            }
            return new Flight(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}