package com.flightbooking.NotificationService.service;

import com.flightbooking.NotificationService.dto.BookingEventDTO;
import com.flightbooking.NotificationService.dto.NotificationDTOs.*;
import com.flightbooking.NotificationService.entity.Notification;

import java.util.List;

public interface NotificationService {

    // Triggered by Kafka consumer
    void handleBookingConfirmed(BookingEventDTO event);

    void handleBookingCancelled(BookingEventDTO event);

    // REST API methods
    NotificationResponse sendBookingConfirmationNotification(BookingConfirmationRequest request);

    NotificationResponse sendBookingCancellationNotification(BookingCancellationRequest request);

    NotificationResponse sendEmailNotification(DirectEmailRequest request);

    List<NotificationResponse> getUserNotifications(Long userId);

    List<NotificationResponse> getNotificationsByBooking(Long bookingId);

    NotificationResponse retryFailedNotification(Long notificationId);

    void sendEmailNotification();

    void sendSMSNotification();

    void saveNotificationRecord(Notification notification);
}
