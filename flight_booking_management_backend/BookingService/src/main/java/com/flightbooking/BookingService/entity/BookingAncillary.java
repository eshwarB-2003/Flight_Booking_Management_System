package com.flightbooking.BookingService.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "booking_ancillary")
@Data
public class BookingAncillary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long bookingId;

    private Long itemId;

    private Integer quantity;
}