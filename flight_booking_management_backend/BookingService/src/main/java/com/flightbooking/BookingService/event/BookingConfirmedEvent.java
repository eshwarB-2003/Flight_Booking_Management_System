package com.flightbooking.BookingService.event;

import lombok.AllArgsConstructor;
import lombok.Data;

public class BookingConfirmedEvent {

    private Long bookingId;
    private Long userId;
    private Long flightId;
    
	public BookingConfirmedEvent(Long bookingId, Long userId, Long flightId) {
		super();
		this.bookingId = bookingId;
		this.userId = userId;
		this.flightId = flightId;
	}
	public Long getBookingId() {
		return bookingId;
	}
	public void setBookingId(Long bookingId) {
		this.bookingId = bookingId;
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
}