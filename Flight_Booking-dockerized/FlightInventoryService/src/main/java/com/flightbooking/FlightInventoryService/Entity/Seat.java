package com.flightbooking.FlightInventoryService.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table (name = "seat")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seatId;
    @Column(nullable = false)
    private String seatNumber;
    @Column(nullable = false)
    private String seatType;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SeatStatus seatStatus;
    private Double price;
    private Double finalPrice;
    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private FlightSchedule schedule;
    @Column(name = "lock_time", nullable = true)
    private LocalDateTime lockTime;
    @ManyToOne
    @JoinColumn(name = "class_id")
    private AircraftClass aircraftClass;
	public Long getSeatId() {
		return seatId;
	}
	public void setSeatId(Long seatId) {
		this.seatId = seatId;
	}
	public String getSeatNumber() {
		return seatNumber;
	}
	public void setSeatNumber(String seatNumber) {
		this.seatNumber = seatNumber;
	}
	public String getSeatType() {
		return seatType;
	}
	public void setSeatType(String seatType) {
		this.seatType = seatType;
	}
	public SeatStatus getSeatStatus() {
		return seatStatus;
	}
	public void setSeatStatus(SeatStatus seatStatus) {
		this.seatStatus = seatStatus;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getFinalPrice() {
		return finalPrice;
	}
	public void setFinalPrice(Double finalPrice) {
		this.finalPrice = finalPrice;
	}
	public FlightSchedule getSchedule() {
		return schedule;
	}
	public void setSchedule(FlightSchedule schedule) {
		this.schedule = schedule;
	}
	public LocalDateTime getLockTime() {
		return lockTime;
	}
	public void setLockTime(LocalDateTime lockTime) {
		this.lockTime = lockTime;
	}
	public AircraftClass getAircraftClass() {
		return aircraftClass;
	}
	public void setAircraftClass(AircraftClass aircraftClass) {
		this.aircraftClass = aircraftClass;
	}
    
    

}