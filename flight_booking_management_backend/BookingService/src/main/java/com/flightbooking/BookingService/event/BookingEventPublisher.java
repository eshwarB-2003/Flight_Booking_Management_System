package com.flightbooking.BookingService.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.flightbooking.BookingService.dto.BookingEventDto;

@Component
public class BookingEventPublisher {

    private static final Logger log = LoggerFactory.getLogger(BookingEventPublisher.class);

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${kafka.topics.booking-confirmed}")
    private String bookingConfirmedTopic;

    @Value("${kafka.topics.booking-cancelled}")
    private String bookingCancelledTopic;

    public BookingEventPublisher(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishConfirmed(BookingEventDto event) {
        log.info("Publishing booking-confirmed event for bookingId={}", event.getBookingId());
        kafkaTemplate.send(bookingConfirmedTopic, String.valueOf(event.getBookingId()), event);
    }

    public void publishCancelled(BookingEventDto event) {
        log.info("Publishing booking-cancelled event for bookingId={}", event.getBookingId());
        kafkaTemplate.send(bookingCancelledTopic, String.valueOf(event.getBookingId()), event);
    }

    // Keep old method for backward compatibility — routes to confirmed
    public void publish(BookingConfirmedEvent event) {
        log.info("Legacy publish called for bookingId={}", event.getBookingId());
    }
}