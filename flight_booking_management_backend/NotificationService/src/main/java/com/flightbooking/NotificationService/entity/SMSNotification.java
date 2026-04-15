package com.flightbooking.NotificationService.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "sms_notifications")
@PrimaryKeyJoinColumn(name = "notification_id")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SMSNotification extends Notification {

    @Column(nullable = false)
    private String phoneNumber;

    private String bookingReference;
    private String flightNumber;
    private String departureTime;
    private String passengerName;
    
    

    // ---------------------------------------------------------------
    // Domain method
    // ---------------------------------------------------------------

    public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getBookingReference() {
		return bookingReference;
	}

	public void setBookingReference(String bookingReference) {
		this.bookingReference = bookingReference;
	}

	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	public String getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}

	public String getPassengerName() {
		return passengerName;
	}

	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
	}

	public void sendSMS() {
        this.send();
    }

    @Override
    public String generateMessage() {
        return String.format(
            "Hi %s! Booking %s confirmed. Flight %s departs %s. Safe travels! - FlightBooking",
            passengerName, bookingReference, flightNumber, departureTime
        );
    }
}
