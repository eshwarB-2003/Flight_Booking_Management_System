package com.flightbooking.BookingService.event;

import org.springframework.stereotype.Component;

@Component
public class BookingEventPublisher {

    public void publish(BookingConfirmedEvent event){
        System.out.println("Booking confirmed event published: " + event);
    }
}