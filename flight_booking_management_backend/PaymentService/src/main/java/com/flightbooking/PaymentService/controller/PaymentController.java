package com.flightbooking.PaymentService.controller;

import com.flightbooking.PaymentService.dto.PaymentRequestDto;
import com.flightbooking.PaymentService.dto.PaymentResponseDto;
import com.flightbooking.PaymentService.entity.Payment;
import com.flightbooking.PaymentService.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<PaymentResponseDto> createPayment(@RequestBody PaymentRequestDto request) {
        return ResponseEntity.ok(paymentService.createPayment(request));
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<Payment> getPayment(@PathVariable Long paymentId) {
        return ResponseEntity.ok(paymentService.getPaymentById(paymentId));
    }
}