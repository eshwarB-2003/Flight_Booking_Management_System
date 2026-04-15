package com.flightbooking.NotificationService.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flightbooking.NotificationService.enums.BookingStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookingEventDTO {

    private Long bookingId;
    private Long userId;
    private Long flightId;
    private BookingStatus bookingStatus;
    private LocalDateTime timestamp;
    private String bookingReference;
    private String passengerName;
    private String passengerEmail;
    private String passengerPhone;
    private String flightNumber;
    private String origin;
    private String destination;
    private String departureTime;
    private String arrivalTime;
    private List<String> seatNumbers;
    private Double totalAmount;

    // ---------------------------------------------------------------
    // Getters and Setters
    // ---------------------------------------------------------------

    public Long getBookingId() { return bookingId; }
    public void setBookingId(Long bookingId) { this.bookingId = bookingId; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getFlightId() { return flightId; }
    public void setFlightId(Long flightId) { this.flightId = flightId; }

    public BookingStatus getBookingStatus() { return bookingStatus; }
    public void setBookingStatus(BookingStatus bookingStatus) { this.bookingStatus = bookingStatus; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public String getBookingReference() { return bookingReference; }
    public void setBookingReference(String bookingReference) { this.bookingReference = bookingReference; }

    public String getPassengerName() { return passengerName; }
    public void setPassengerName(String passengerName) { this.passengerName = passengerName; }

    public String getPassengerEmail() { return passengerEmail; }
    public void setPassengerEmail(String passengerEmail) { this.passengerEmail = passengerEmail; }

    public String getPassengerPhone() { return passengerPhone; }
    public void setPassengerPhone(String passengerPhone) { this.passengerPhone = passengerPhone; }

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

    public List<String> getSeatNumbers() { return seatNumbers; }
    public void setSeatNumbers(List<String> seatNumbers) { this.seatNumbers = seatNumbers; }

    public Double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(Double totalAmount) { this.totalAmount = totalAmount; }

    public String toJson() {
        return String.format(
            "{\"bookingId\":%d,\"userId\":%d,\"flightId\":%d,\"status\":\"%s\",\"timestamp\":\"%s\"}",
            bookingId, userId, flightId, bookingStatus, timestamp
        );
    }

    // ---------------------------------------------------------------
    // Manual Builder
    // ---------------------------------------------------------------

    public static BookingEventDTOBuilder builder() {
        return new BookingEventDTOBuilder();
    }

    public static class BookingEventDTOBuilder {
        private Long bookingId;
        private Long userId;
        private Long flightId;
        private BookingStatus bookingStatus;
        private LocalDateTime timestamp;
        private String bookingReference;
        private String passengerName;
        private String passengerEmail;
        private String passengerPhone;
        private String flightNumber;
        private String origin;
        private String destination;
        private String departureTime;
        private String arrivalTime;
        private List<String> seatNumbers;
        private Double totalAmount;

        public BookingEventDTOBuilder bookingId(Long bookingId) { 
        	this.bookingId = bookingId; 
        	return this; 
        	}
        public BookingEventDTOBuilder userId(Long userId) { 
        	this.userId = userId; 
        	return this; 
        	}
        public BookingEventDTOBuilder flightId(Long flightId) { this.flightId = flightId; return this; }
        public BookingEventDTOBuilder bookingStatus(BookingStatus bookingStatus) { this.bookingStatus = bookingStatus; return this; }
        public BookingEventDTOBuilder timestamp(LocalDateTime timestamp) { this.timestamp = timestamp; return this; }
        public BookingEventDTOBuilder bookingReference(String bookingReference) { this.bookingReference = bookingReference; return this; }
        public BookingEventDTOBuilder passengerName(String passengerName) { this.passengerName = passengerName; return this; }
        public BookingEventDTOBuilder passengerEmail(String passengerEmail) { this.passengerEmail = passengerEmail; return this; }
        public BookingEventDTOBuilder passengerPhone(String passengerPhone) { this.passengerPhone = passengerPhone; return this; }
        public BookingEventDTOBuilder flightNumber(String flightNumber) { this.flightNumber = flightNumber; return this; }
        public BookingEventDTOBuilder origin(String origin) { this.origin = origin; return this; }
        public BookingEventDTOBuilder destination(String destination) { this.destination = destination; return this; }
        public BookingEventDTOBuilder departureTime(String departureTime) { this.departureTime = departureTime; return this; }
        public BookingEventDTOBuilder arrivalTime(String arrivalTime) { this.arrivalTime = arrivalTime; return this; }
        public BookingEventDTOBuilder seatNumbers(List<String> seatNumbers) { this.seatNumbers = seatNumbers; return this; }
        public BookingEventDTOBuilder totalAmount(Double totalAmount) { this.totalAmount = totalAmount; return this; }

        public BookingEventDTO build() {
            BookingEventDTO dto = new BookingEventDTO();
            dto.setBookingId(bookingId);
            dto.setUserId(userId);
            dto.setFlightId(flightId);
            dto.setBookingStatus(bookingStatus);
            dto.setTimestamp(timestamp);
            dto.setBookingReference(bookingReference);
            dto.setPassengerName(passengerName);
            dto.setPassengerEmail(passengerEmail);
            dto.setPassengerPhone(passengerPhone);
            dto.setFlightNumber(flightNumber);
            dto.setOrigin(origin);
            dto.setDestination(destination);
            dto.setDepartureTime(departureTime);
            dto.setArrivalTime(arrivalTime);
            dto.setSeatNumbers(seatNumbers);
            dto.setTotalAmount(totalAmount);
            return dto;
        }
    }
}
