package com.flightbooking.NotificationService.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "email_notifications")
@PrimaryKeyJoinColumn(name = "notification_id")
public class EmailNotification extends Notification {

    @Column(nullable = false)
    private String subject;

    private String attachmentPath;
    private String bookingReference;
    private String flightNumber;
    private String origin;
    private String destination;
    private String departureTime;
    private String arrivalTime;
    private String seatNumbers;
    private Double totalAmount;
    private String passengerName;

    public EmailNotification() {
        super();
    }

    public void sendEmail() {
        this.send();   // send() is inherited from Notification
    }

    @Override
    public String generateMessage() {
        return String.format(
            "Dear %s,\n\nYour booking %s has been confirmed.\n" +
            "Flight: %s | %s → %s\n" +
            "Departure: %s | Arrival: %s\n" +
            "Seats: %s\n" +
            "Total Amount: €%.2f\n\n" +
            "Thank you for booking with us!",
            passengerName, bookingReference, flightNumber,
            origin, destination, departureTime, arrivalTime,
            seatNumbers, totalAmount != null ? totalAmount : 0.0
        );
    }

    // Getters and Setters
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getAttachmentPath() { return attachmentPath; }
    public void setAttachmentPath(String attachmentPath) { this.attachmentPath = attachmentPath; }

    public String getBookingReference() { return bookingReference; }
    public void setBookingReference(String bookingReference) { this.bookingReference = bookingReference; }

    public String getFlightNumber() { return flightNumber; }
    public void setFlightNumber(String flightNumber) { this.flightNumber = flightNumber; }

    public String getOrigin() { return origin; }
    public void setOrigin(String origin) { this.origin = origin; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public String getDepartureTime() { return departureTime; }
    public void setDepartureTime(String departureTime) { this.departureTime = departureTime; }

    public String getArrivalTime() { return arrivalTime; }
    public void setArrivalTime(String arrivalTime) { this.arrivalTime = arrivalTime; }

    public String getSeatNumbers() { return seatNumbers; }
    public void setSeatNumbers(String seatNumbers) { this.seatNumbers = seatNumbers; }

    public Double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(Double totalAmount) { this.totalAmount = totalAmount; }

    public String getPassengerName() { return passengerName; }
    public void setPassengerName(String passengerName) { this.passengerName = passengerName; }
}