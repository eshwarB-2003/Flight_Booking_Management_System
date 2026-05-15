package com.flightbooking.BookingService.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "booking")
@Data
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    private String bookingReference;

    private Long userId;

    private Long flightId;

    private String bookingStatus;

    private Double totalAmount;

    private LocalDateTime createdAt;
    
    private String passengerName;
    private String passengerEmail;
    private String passengerPhone;
    
    public Booking() {
    	
    }

    public Booking(Long bookingId, String bookingReference, Long userId, Long flightId, String bookingStatus,
			Double totalAmount, LocalDateTime createdAt, String passengerName, String passengerEmail,
			String passengerPhone) {
		super();
		this.bookingId = bookingId;
		this.bookingReference = bookingReference;
		this.userId = userId;
		this.flightId = flightId;
		this.bookingStatus = bookingStatus;
		this.totalAmount = totalAmount;
		this.createdAt = createdAt;
		this.passengerName = passengerName;
		this.passengerEmail = passengerEmail;
		this.passengerPhone = passengerPhone;
	}

	public Long getBookingId() {
		return bookingId;
	}

	public void setBookingId(Long bookingId) {
		this.bookingId = bookingId;
	}

	public String getBookingReference() {
		return bookingReference;
	}

	public void setBookingReference(String bookingReference) {
		this.bookingReference = bookingReference;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getFlightId() {
		return flightId;
	}

	public void setFlightId(Long flightId) {
		this.flightId = flightId;
	}

	public String getBookingStatus() {
		return bookingStatus;
	}

	public void setBookingStatus(String bookingStatus) {
		this.bookingStatus = bookingStatus;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public String getPassengerName() {
		return passengerName;
	}

	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
	}

	public String getPassengerEmail() {
		return passengerEmail;
	}

	public void setPassengerEmail(String passengerEmail) {
		this.passengerEmail = passengerEmail;
	}

	public String getPassengerPhone() {
		return passengerPhone;
	}

	public void setPassengerPhone(String passengerPhone) {
		this.passengerPhone = passengerPhone;
	}
}