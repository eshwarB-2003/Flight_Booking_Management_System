package com.flightbooking.PaymentService.strategy;

import com.flightbooking.PaymentService.dto.PaymentRequestDto;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class NetBankingPaymentStrategy implements PaymentStrategy {

    @Override
    public boolean processPayment(PaymentRequestDto request) {
        return request.getBankName() != null && !request.getBankName().isBlank();
    }

    @Override
    public String generateTransactionReference() {
        return "NB-" + UUID.randomUUID();
    }
}