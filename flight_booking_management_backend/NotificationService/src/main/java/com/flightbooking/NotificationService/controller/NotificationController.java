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

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@Slf4j
public class NotificationController {
	private static final Logger log = LoggerFactory.getLogger(NotificationController.class);
	
	@Autowired
    private NotificationService notificationService;

 
    // POST /api/notifications/booking-confirmation
   
    @PostMapping("/booking-confirmation")
    public ResponseEntity<ApiResponse<NotificationResponse>> sendBookingConfirmation(
            @Valid @RequestBody BookingConfirmationRequest request) {

        log.info("POST /booking-confirmation | bookingId={}", request.getBookingId());

        NotificationResponse response = notificationService.sendBookingConfirmationNotification(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Booking confirmation notification sent successfully.", response));
    }

   
    // POST /api/notifications/booking-cancelled
   
    @PostMapping("/booking-cancelled")
    public ResponseEntity<ApiResponse<NotificationResponse>> sendBookingCancellation(
            @Valid @RequestBody BookingCancellationRequest request) {

        log.info("POST /booking-cancelled | bookingId={}", request.getBookingId());

        NotificationResponse response = notificationService.sendBookingCancellationNotification(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Booking cancellation notification sent successfully.", response));
    }

    //POST /api/notifications/email
    @PostMapping("/email")
    public ResponseEntity<ApiResponse<NotificationResponse>> sendEmailNotification(
            @Valid @RequestBody DirectEmailRequest request) {

        log.info("POST /email | recipient={}", request.getRecipientEmail());

        NotificationResponse response = notificationService.sendEmailNotification(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Email notification sent successfully.", response));
    }

    // GET /api/notifications/user/{userId}

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

   
    // GET /api/notifications/booking/{bookingId}
    
    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<ApiResponse<List<NotificationResponse>>> getNotificationsByBooking(
            @PathVariable Long bookingId) {

        log.info("GET /booking/{}", bookingId);

        List<NotificationResponse> notifications = notificationService.getNotificationsByBooking(bookingId);

        return ResponseEntity.ok(
                ApiResponse.success("Fetched " + notifications.size() + " notification(s) for bookingId=" + bookingId,
                        notifications));
    }

   
    // POST /api/notifications/{notificationId}/retry
    
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
