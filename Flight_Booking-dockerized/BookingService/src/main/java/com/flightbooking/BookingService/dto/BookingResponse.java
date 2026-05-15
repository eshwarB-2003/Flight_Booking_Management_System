package com.flightbooking.BookingService.dto;

public class BookingResponse {

    private Long bookingId;
    private String bookingReference;
    private String bookingStatus;
    private Double totalAmount;

    public Long getBookingId() { return bookingId; }
    public void setBookingId(Long bookingId) { this.bookingId = bookingId; }
    public String getBookingReference() { return bookingReference; }
    public void setBookingReference(String bookingReference) { this.bookingReference = bookingReference; }
    public String getBookingStatus() { return bookingStatus; }
    public void setBookingStatus(String bookingStatus) { this.bookingStatus = bookingStatus; }
    public Double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(Double totalAmount) { this.totalAmount = totalAmount; }

    public static BookingResponseBuilder builder() {
        return new BookingResponseBuilder();
    }

    public static class BookingResponseBuilder {
        private Long bookingId;
        private String bookingReference;
        private String bookingStatus;
        private Double totalAmount;

        public BookingResponseBuilder bookingId(Long bookingId) {
            this.bookingId = bookingId;
            return this;
        }
        public BookingResponseBuilder bookingReference(String bookingReference) {
            this.bookingReference = bookingReference;
            return this;
        }
        public BookingResponseBuilder bookingStatus(String bookingStatus) {
            this.bookingStatus = bookingStatus;
            return this;
        }
        public BookingResponseBuilder totalAmount(Double totalAmount) {
            this.totalAmount = totalAmount;
            return this;
        }

        public BookingResponse build() {
            BookingResponse response = new BookingResponse();
            response.setBookingId(bookingId);
            response.setBookingReference(bookingReference);
            response.setBookingStatus(bookingStatus);
            response.setTotalAmount(totalAmount);
            return response;
        }
    }
}