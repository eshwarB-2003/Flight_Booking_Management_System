package com.flightbooking.PaymentService.state;

import com.flightbooking.PaymentService.entity.Payment;
import com.flightbooking.PaymentService.enums.PaymentStatus;
import org.springframework.stereotype.Component;

@Component
public class PendingState implements PaymentState {

    @Override
    public void handle(Payment payment) {
        payment.setPaymentStatus(PaymentStatus.PENDING);
    }
}