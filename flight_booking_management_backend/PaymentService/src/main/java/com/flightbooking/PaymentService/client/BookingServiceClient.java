package com.flightbooking.PaymentService.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class BookingServiceClient {

    private final RestTemplate restTemplate;

    public BookingServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void confirmBooking(Long bookingId) {
        String url = "http://localhost:8082/api/bookings/" + bookingId + "/confirm";
        try {
            restTemplate.postForEntity(url, null, Void.class);
            System.out.println("Booking confirmed for bookingId: " + bookingId);
        } catch (Exception e) {
            System.out.println("Booking confirmation API call failed: " + e.getMessage());
        }
    }

    public void cancelBooking(Long bookingId) {
        String url = "http://localhost:8082/api/bookings/" + bookingId + "/cancel";
        try {
            restTemplate.postForEntity(url, null, Void.class);
            System.out.println("Booking cancelled for bookingId: " + bookingId);
        } catch (Exception e) {
            System.out.println("Booking cancel API call failed: " + e.getMessage());
        }
    }
}