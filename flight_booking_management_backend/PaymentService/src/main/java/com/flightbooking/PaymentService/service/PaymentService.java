package com.flightbooking.PaymentService.service;

import com.flightbooking.PaymentService.client.BookingServiceClient;
import com.flightbooking.PaymentService.dto.PaymentRequestDto;
import com.flightbooking.PaymentService.dto.PaymentResponseDto;
import com.flightbooking.PaymentService.entity.Payment;
import com.flightbooking.PaymentService.repository.PaymentRepository;
import com.flightbooking.PaymentService.state.PaymentStateContext;
import com.flightbooking.PaymentService.strategy.PaymentStrategy;
import com.flightbooking.PaymentService.strategy.PaymentStrategyFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentStrategyFactory strategyFactory;
    private final PaymentStateContext stateContext;
    private final BookingServiceClient bookingServiceClient;

    public PaymentService(PaymentRepository paymentRepository,
                          PaymentStrategyFactory strategyFactory,
                          PaymentStateContext stateContext,
                          BookingServiceClient bookingServiceClient) {
        this.paymentRepository = paymentRepository;
        this.strategyFactory = strategyFactory;
        this.stateContext = stateContext;
        this.bookingServiceClient = bookingServiceClient;
    }

    public PaymentResponseDto createPayment(PaymentRequestDto request) {
        Payment payment = new Payment();
        payment.setBookingId(request.getBookingId());
        payment.setAmount(request.getAmount());
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setPaymentDate(LocalDateTime.now());

        stateContext.setPending(payment);

        PaymentStrategy strategy = strategyFactory.getStrategy(request.getPaymentMethod());
        boolean success = strategy.processPayment(request);

        if (success) {
            stateContext.setApproved(payment);
            payment.setTransactionReference(strategy.generateTransactionReference());
            payment.setGatewayResponse("Payment successful");

            bookingServiceClient.confirmBooking(payment.getBookingId());
        } else {
            stateContext.setFailed(payment);
            payment.setTransactionReference(null);
            payment.setGatewayResponse("Payment failed");

            bookingServiceClient.cancelBooking(payment.getBookingId());
        }

        Payment savedPayment = paymentRepository.save(payment);

        return new PaymentResponseDto(
                savedPayment.getPaymentId(),
                savedPayment.getBookingId(),
                savedPayment.getPaymentStatus(),
                savedPayment.getGatewayResponse(),
                savedPayment.getTransactionReference()
        );
    }

    public Payment getPaymentById(Long paymentId) {
        return paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
    }
}