package com.flightbooking.BookingService.controller;

import com.flightbooking.BookingService.dto.AncillaryRequest;
import com.flightbooking.BookingService.dto.BookingResponse;
import com.flightbooking.BookingService.dto.CreateBookingRequest;
import com.flightbooking.BookingService.entity.BookingAncillary;
import com.flightbooking.BookingService.service.BookingService;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {
	@Autowired
    private BookingService bookingService;

    @PostMapping
    public BookingResponse createBooking(
            @RequestBody CreateBookingRequest request
    ){
        return bookingService.createBooking(request);
    }

    @PostMapping("/{bookingId}/confirm") 
    public String confirmBooking(@PathVariable Long bookingId) {
        bookingService.confirmBooking(bookingId);
        return "Booking confirmed";
    }

    @PostMapping("/{bookingId}/cancel")  
    public String cancelBooking(@PathVariable Long bookingId) {
        bookingService.cancelBooking(bookingId);
        return "Booking cancelled";
    }

    @GetMapping("/{bookingId}")
    public BookingResponse getBooking(
            @PathVariable Long bookingId
    ){
        return bookingService.getBooking(bookingId);
    }

    @GetMapping("/my-bookings/{userId}")
    public List<BookingResponse> getMyBookings(
            @PathVariable Long userId
    ){
        return bookingService.getMyBookings(userId);
    }

    @PostMapping("/{bookingId}/ancillaries")
    public String addAncillary(
            @PathVariable Long bookingId,
            @RequestBody AncillaryRequest request
    ){
        return bookingService.addAncillary(bookingId, request);
    }

    @GetMapping("/{bookingId}/ancillaries")
    public List<BookingAncillary> getAncillaries(
            @PathVariable Long bookingId
    ){
        return bookingService.getAncillaries(bookingId);
    }

    @DeleteMapping("/ancillaries/{id}")
    public String removeAncillary(
            @PathVariable Long id
    ){
        return bookingService.removeAncillary(id);
    }
}