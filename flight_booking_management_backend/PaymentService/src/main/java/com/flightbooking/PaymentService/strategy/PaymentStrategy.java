package com.flightbooking.PaymentService.strategy;

import com.flightbooking.PaymentService.dto.PaymentRequestDto;

public interface PaymentStrategy {
    boolean processPayment(PaymentRequestDto request);
    String generateTransactionReference();
}