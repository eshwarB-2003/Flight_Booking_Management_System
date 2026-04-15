package com.flightbooking.PaymentService.strategy;

import com.flightbooking.PaymentService.enums.PaymentMethod;
import org.springframework.stereotype.Component;

@Component
public class PaymentStrategyFactory {

    private final CardPaymentStrategy cardPaymentStrategy;
    private final UpiPaymentStrategy upiPaymentStrategy;
    private final NetBankingPaymentStrategy netBankingPaymentStrategy;

    public PaymentStrategyFactory(CardPaymentStrategy cardPaymentStrategy,
                                  UpiPaymentStrategy upiPaymentStrategy,
                                  NetBankingPaymentStrategy netBankingPaymentStrategy) {
        this.cardPaymentStrategy = cardPaymentStrategy;
        this.upiPaymentStrategy = upiPaymentStrategy;
        this.netBankingPaymentStrategy = netBankingPaymentStrategy;
    }

    public PaymentStrategy getStrategy(PaymentMethod method) {
        return switch (method) {
            case CARD -> cardPaymentStrategy;
            case UPI -> upiPaymentStrategy;
            case NETBANKING -> netBankingPaymentStrategy;
        };
    }
}