package com.flightbooking.PaymentService.state;

import com.flightbooking.PaymentService.entity.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentStateContext {

    private final PendingState pendingState;
    private final ApprovalState approvalState;
    private final FailedState failedState;

    public PaymentStateContext(PendingState pendingState,
                               ApprovalState approvalState,
                               FailedState failedState) {
        this.pendingState = pendingState;
        this.approvalState = approvalState;
        this.failedState = failedState;
    }

    public void setPending(Payment payment) {
        pendingState.handle(payment);
    }

    public void setApproved(Payment payment) {
        approvalState.handle(payment);
    }

    public void setFailed(Payment payment) {
        failedState.handle(payment);
    }
}