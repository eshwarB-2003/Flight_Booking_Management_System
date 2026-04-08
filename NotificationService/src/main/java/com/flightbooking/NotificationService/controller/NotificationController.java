package com.flightbooking.NotificationService.controller;

import com.flightbooking.NotificationService.dto.NotificationDTOs.*;
import com.flightbooking.NotificationService.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for all Notification Service endpoints.
 *
 * Endpoints:
 *  POST  /api/v1/notifications/booking-confirmation
 *  POST  /api/v1/notifications/booking-cancelled
 *  POST  /api/v1/notifications/email
 *  GET   /api/v1/notifications/user/{userId}
 *  GET   /api/v1/notifications/booking/{bookingId}
 *  POST  /api/v1/notifications/{notificationId}/retry
 */
@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
@Slf4j
public class NotificationController {
	private static final Logger log = LoggerFactory.getLogger(NotificationController.class);
	
	@Autowired
    private NotificationService notificationService;

    // ---------------------------------------------------------------
    // 1. POST /api/v1/notifications/booking-confirmation
    // ---------------------------------------------------------------

    /**
     * Sends a booking confirmation email + SMS.
     * Called after payment is successful and booking is CONFIRMED.
     */
    @PostMapping("/booking-confirmation")
    public ResponseEntity<ApiResponse<NotificationResponse>> sendBookingConfirmation(
            @Valid @RequestBody BookingConfirmationRequest request) {

        log.info("POST /booking-confirmation | bookingId={}", request.getBookingId());

        NotificationResponse response = notificationService.sendBookingConfirmationNotification(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Booking confirmation notification sent successfully.", response));
    }

    // ---------------------------------------------------------------
    // 2. POST /api/v1/notifications/booking-cancelled
    // ---------------------------------------------------------------

    /**
     * Sends a booking cancellation email + SMS.
     * Called after booking status changes to CANCELLED.
     */
    @PostMapping("/booking-cancelled")
    public ResponseEntity<ApiResponse<NotificationResponse>> sendBookingCancellation(
            @Valid @RequestBody BookingCancellationRequest request) {

        log.info("POST /booking-cancelled | bookingId={}", request.getBookingId());

        NotificationResponse response = notificationService.sendBookingCancellationNotification(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Booking cancellation notification sent successfully.", response));
    }

    // ---------------------------------------------------------------
    // 3. POST /api/v1/notifications/email
    // ---------------------------------------------------------------

    /**
     * Sends a custom/direct email notification.
     * Used for general-purpose email delivery.
     */
    @PostMapping("/email")
    public ResponseEntity<ApiResponse<NotificationResponse>> sendEmailNotification(
            @Valid @RequestBody DirectEmailRequest request) {

        log.info("POST /email | recipient={}", request.getRecipientEmail());

        NotificationResponse response = notificationService.sendEmailNotification(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Email notification sent successfully.", response));
    }

    // ---------------------------------------------------------------
    // 4. GET /api/v1/notifications/user/{userId}
    // ---------------------------------------------------------------

    /**
     * Retrieves all notifications for a given user.
     * Ordered by most recent first.
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<NotificationResponse>>> getUserNotifications(
            @PathVariable Long userId) {

        log.info("GET /user/{}", userId);

        List<NotificationResponse> notifications = notificationService.getUserNotifications(userId);

        if (notifications.isEmpty()) {
            return ResponseEntity.ok(
                    ApiResponse.success("No notifications found for userId=" + userId, notifications));
        }

        return ResponseEntity.ok(
                ApiResponse.success("Fetched " + notifications.size() + " notification(s).", notifications));
    }

    // ---------------------------------------------------------------
    // 5. GET /api/v1/notifications/booking/{bookingId}
    // ---------------------------------------------------------------

    /**
     * Retrieves all notifications linked to a specific booking.
     */
    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<ApiResponse<List<NotificationResponse>>> getNotificationsByBooking(
            @PathVariable Long bookingId) {

        log.info("GET /booking/{}", bookingId);

        List<NotificationResponse> notifications = notificationService.getNotificationsByBooking(bookingId);

        return ResponseEntity.ok(
                ApiResponse.success("Fetched " + notifications.size() + " notification(s) for bookingId=" + bookingId,
                        notifications));
    }

    // ---------------------------------------------------------------
    // 6. POST /api/v1/notifications/{notificationId}/retry
    // ---------------------------------------------------------------

    /**
     * Retries a FAILED notification.
     * Returns 400 if the notification is not in FAILED state.
     */
    @PostMapping("/{notificationId}/retry")
    public ResponseEntity<ApiResponse<NotificationResponse>> retryNotification(
            @PathVariable Long notificationId) {

        log.info("POST /{}/retry", notificationId);

        try {
            NotificationResponse response = notificationService.retryFailedNotification(notificationId);
            return ResponseEntity.ok(ApiResponse.success("Notification retry attempted.", response));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }
}
