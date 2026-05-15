package com.flightbooking.NotificationService.service;

import com.flightbooking.NotificationService.dto.BookingEventDTO;

public interface SmsService {

    void sendBookingConfirmationSms(BookingEventDTO event);

    void sendBookingCancellationSms(BookingEventDTO event);

    void sendCheckInReminderSms(Long bookingId, String phoneNumber, String passengerName, String flightNumber, String departureTime, String bookingReference);
    }
