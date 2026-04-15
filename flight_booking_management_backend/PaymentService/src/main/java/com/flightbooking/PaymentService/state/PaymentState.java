package com.flightbooking.PaymentService.state;

import com.flightbooking.PaymentService.entity.Payment;

public interface PaymentState {
    void handle(Payment payment);
}