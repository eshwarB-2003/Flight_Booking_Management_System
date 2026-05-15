package com.flightbooking.PaymentService.state;

import com.flightbooking.PaymentService.entity.Payment;
import com.flightbooking.PaymentService.enums.PaymentStatus;
import org.springframework.stereotype.Component;

@Component
public class ApprovalState implements PaymentState {

    @Override
    public void handle(Payment payment) {
        payment.setPaymentStatus(PaymentStatus.APPROVAL);
    }
}