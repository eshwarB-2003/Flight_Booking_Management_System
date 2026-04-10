package com.flightbooking.BookingService.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "booking")
@Data
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    private String bookingReference;

    private Long userId;

    private Long flightId;

    private String bookingStatus;

    private Double totalAmount;

    private LocalDateTime createdAt;
}