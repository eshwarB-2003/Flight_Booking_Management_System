package com.flightbooking.PaymentService.dto;

import com.flightbooking.PaymentService.enums.PaymentStatus;

public class PaymentResponseDto {

    private Long paymentId;
    private Long bookingId;
    private PaymentStatus status;
    private String message;
    private String transactionReference;

    public PaymentResponseDto() {
    }

    public PaymentResponseDto(Long paymentId, Long bookingId, PaymentStatus status, String message, String transactionReference) {
        this.paymentId = paymentId;
        this.bookingId = bookingId;
        this.status = status;
        this.message = message;
        this.transactionReference = transactionReference;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getTransactionReference() {
        return transactionReference;
    }
}