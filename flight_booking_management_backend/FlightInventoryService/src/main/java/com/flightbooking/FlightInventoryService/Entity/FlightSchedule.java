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
		@Enumerated(EnumType.STRING)
        @Column(nullable = false)
        private ScheduleStatus status;

        @ManyToOne
        @JoinColumn(name = "flight_id")
        private Flight flight;
        @ManyToOne
        @JoinColumn(name = "aircraft_id")
        private Aircraft aircraft;
    }
