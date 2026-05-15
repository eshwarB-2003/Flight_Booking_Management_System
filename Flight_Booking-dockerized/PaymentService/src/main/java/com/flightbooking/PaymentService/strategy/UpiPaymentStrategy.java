package com.flightbooking.PaymentService.strategy;

import com.flightbooking.PaymentService.dto.PaymentRequestDto;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UpiPaymentStrategy implements PaymentStrategy {

    @Override
    public boolean processPayment(PaymentRequestDto request) {
        return request.getUpiId() != null && request.getUpiId().contains("@");
    }

    @Override
    public String generateTransactionReference() {
        return "UPI-" + UUID.randomUUID();
    }
}