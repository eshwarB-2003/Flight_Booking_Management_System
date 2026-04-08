package com.flightbooking.NotificationService.consumer;

import com.flightbooking.NotificationService.dto.BookingEventDTO;
import com.flightbooking.NotificationService.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class BookingEventConsumer {
	
	private static final Logger log = LoggerFactory.getLogger(BookingEventConsumer.class);
	
	@Autowired
    private NotificationService notificationService;

    /**
     * Consumes booking-confirmed events published by the Booking Service.
     *
     * Topic: booking-confirmed
     * Published when: Booking.confirmBooking() → BookingService.publishBookingEvent()
     */
    @KafkaListener(
            topics = "${kafka.topics.booking-confirmed}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void handleBookingConfirmed(
            @Payload BookingEventDTO event,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
            @Header(KafkaHeaders.OFFSET) long offset,
            Acknowledgment acknowledgment) {

        log.info("Received booking-confirmed event | topic={} | partition={} | offset={} | bookingId={}",
                topic, partition, offset, event.getBookingId());

        try {
            notificationService.handleBookingConfirmed(event);
            acknowledgment.acknowledge(); // manual ack — only on success
            log.info("Acknowledged booking-confirmed event for bookingId={}", event.getBookingId());
        } catch (Exception e) {
            log.error("Error processing booking-confirmed event for bookingId={}: {}",
                    event.getBookingId(), e.getMessage(), e);
            // Do NOT acknowledge — Kafka will retry per DefaultErrorHandler back-off
        }
    }

    /**
     * Consumes booking-cancelled events published by the Booking Service.
     *
     * Topic: booking-cancelled
     * Published when: Booking.cancelBooking() → BookingService.publishBookingEvent()
     */
    @KafkaListener(
            topics = "${kafka.topics.booking-cancelled}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void handleBookingCancelled(
            @Payload BookingEventDTO event,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
            @Header(KafkaHeaders.OFFSET) long offset,
            Acknowledgment acknowledgment) {

        log.info("Received booking-cancelled event | topic={} | partition={} | offset={} | bookingId={}",
                topic, partition, offset, event.getBookingId());

        try {
            notificationService.handleBookingCancelled(event);
            acknowledgment.acknowledge();
            log.info("Acknowledged booking-cancelled event for bookingId={}", event.getBookingId());
        } catch (Exception e) {
            log.error("Error processing booking-cancelled event for bookingId={}: {}",
                    event.getBookingId(), e.getMessage(), e);
        }
    }
}
