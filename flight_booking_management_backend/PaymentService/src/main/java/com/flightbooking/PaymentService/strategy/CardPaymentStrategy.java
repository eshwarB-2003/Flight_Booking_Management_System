package com.flightbooking.PaymentService.strategy;

import com.flightbooking.PaymentService.dto.PaymentRequestDto;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CardPaymentStrategy implements PaymentStrategy {

    @Override
    public boolean processPayment(PaymentRequestDto request) {
        return request.getCardNumber() != null && request.getCardNumber().length() >= 12;
    }

    @Override
    public String generateTransactionReference() {
        return "CARD-" + UUID.randomUUID();
    }
}