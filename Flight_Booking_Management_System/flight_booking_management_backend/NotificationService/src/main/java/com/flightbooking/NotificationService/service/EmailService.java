package com.flightbooking.NotificationService.service;

import com.flightbooking.NotificationService.dto.BookingEventDTO;
import com.flightbooking.NotificationService.dto.NotificationDTOs.DirectEmailRequest;

public interface EmailService {

    /**
     * Send booking confirmation email.
     */
    void sendBookingConfirmationEmail(BookingEventDTO event);

    /**
     * Send booking cancellation email.
     */
    void sendBookingCancellationEmail(BookingEventDTO event);

    /**
     * Send a direct/custom email — used by POST /api/v1/notifications/email.
     */
    void sendDirectEmail(DirectEmailRequest request);

    /**
     * Send a check-in reminder email 24h before departure.
     */
    void sendCheckInReminderEmail(Long bookingId, String recipientEmail,
                                  String passengerName, String flightNumber,
                                  String departureTime, String bookingReference);
}
