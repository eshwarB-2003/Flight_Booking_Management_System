package com.flightbooking.NotificationService.service.impl;

import com.flightbooking.NotificationService.dto.BookingEventDTO;
import com.flightbooking.NotificationService.dto.NotificationDTOs.*;
import com.flightbooking.NotificationService.entity.Notification;
import com.flightbooking.NotificationService.enums.BookingStatus;
import com.flightbooking.NotificationService.enums.NotificationStatus;
import com.flightbooking.NotificationService.enums.NotificationType;
import com.flightbooking.NotificationService.repo.NotificationRepository;
import com.flightbooking.NotificationService.service.EmailService;
import com.flightbooking.NotificationService.service.NotificationService;
import com.flightbooking.NotificationService.service.SmsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {
	
	private static final Logger log = LoggerFactory.getLogger(NotificationServiceImpl.class);
	@Autowired
    private EmailService emailService;
	@Autowired
    private SmsService smsService;
	@Autowired
    private NotificationRepository notificationRepository;

    // ---------------------------------------------------------------
    // Kafka event handlers
    // ---------------------------------------------------------------

    /**
     * Called by KafkaConsumer when a booking-confirmed event arrives.
     * Sends both email and SMS (if phone available).
     */
    @Override
    @Transactional
    public void handleBookingConfirmed(BookingEventDTO event) {
        log.info("Handling booking confirmed event for bookingId={}", event.getBookingId());

        // Guard: avoid duplicate notifications
        if (notificationRepository.existsByBookingIdAndNotificationType(
                event.getBookingId(), NotificationType.BOOKING_CONFIRMATION)) {
            log.warn("Confirmation notification already sent for bookingId={}, skipping.", event.getBookingId());
            return;
        }

        sendEmailNotification(event, BookingStatus.CONFIRMED);
        sendSMSNotification(event, BookingStatus.CONFIRMED);
    }

    /**
     * Called by KafkaConsumer when a booking-cancelled event arrives.
     */
    @Override
    @Transactional
    public void handleBookingCancelled(BookingEventDTO event) {
        log.info("Handling booking cancelled event for bookingId={}", event.getBookingId());

        if (notificationRepository.existsByBookingIdAndNotificationType(
                event.getBookingId(), NotificationType.BOOKING_CANCELLATION)) {
            log.warn("Cancellation notification already sent for bookingId={}, skipping.", event.getBookingId());
            return;
        }

        sendEmailNotification(event, BookingStatus.CANCELLED);
        sendSMSNotification(event, BookingStatus.CANCELLED);
    }

    // ---------------------------------------------------------------
    // REST API handlers
    // ---------------------------------------------------------------

    /**
     * POST /api/v1/notifications/booking-confirmation
     */
    @Override
    @Transactional
    public NotificationResponse sendBookingConfirmationNotification(BookingConfirmationRequest request) {
        log.info("REST: Sending booking confirmation for bookingId={}", request.getBookingId());

        BookingEventDTO event = BookingEventDTO.builder()
                .bookingId(request.getBookingId())
                .userId(request.getUserId())
                .bookingReference(request.getBookingReference())
                .passengerName(request.getPassengerName())
                .passengerEmail(request.getPassengerEmail())
                .passengerPhone(request.getPassengerPhone())
                .flightNumber(request.getFlightNumber())
                .origin(request.getOrigin())
                .destination(request.getDestination())
                .departureTime(request.getDepartureTime())
                .arrivalTime(request.getArrivalTime())
                .seatNumbers(request.getSeatNumbers() != null
                        ? List.of(request.getSeatNumbers().split(","))
                        : List.of())
                .totalAmount(request.getTotalAmount())
                .bookingStatus(BookingStatus.CONFIRMED)
                .build();

        emailService.sendBookingConfirmationEmail(event);
        smsService.sendBookingConfirmationSms(event);

        // Return latest saved record for this booking + type
        return notificationRepository
                .findByBookingIdOrderByCreatedAtDesc(request.getBookingId())
                .stream()
                .filter(n -> n.getNotificationType() == NotificationType.BOOKING_CONFIRMATION)
                .findFirst()
                .map(this::toResponse)
                .orElse(NotificationResponse.builder()
                        .bookingId(request.getBookingId())
                        .status("SENT")
                        .message("Confirmation notifications dispatched.")
                        .build());
    }

    /**
     * POST /api/v1/notifications/booking-cancelled
     */
    @Override
    @Transactional
    public NotificationResponse sendBookingCancellationNotification(BookingCancellationRequest request) {
        log.info("REST: Sending booking cancellation for bookingId={}", request.getBookingId());

        BookingEventDTO event = BookingEventDTO.builder()
                .bookingId(request.getBookingId())
                .userId(request.getUserId())
                .bookingReference(request.getBookingReference())
                .passengerName(request.getPassengerName())
                .passengerEmail(request.getPassengerEmail())
                .passengerPhone(request.getPassengerPhone())
                .flightNumber(request.getFlightNumber())
                .departureTime(request.getDepartureTime())
                .totalAmount(request.getRefundAmount())
                .bookingStatus(BookingStatus.CANCELLED)
                .build();

        emailService.sendBookingCancellationEmail(event);
        smsService.sendBookingCancellationSms(event);

        return notificationRepository
                .findByBookingIdOrderByCreatedAtDesc(request.getBookingId())
                .stream()
                .filter(n -> n.getNotificationType() == NotificationType.BOOKING_CANCELLATION)
                .findFirst()
                .map(this::toResponse)
                .orElse(NotificationResponse.builder()
                        .bookingId(request.getBookingId())
                        .status("SENT")
                        .message("Cancellation notifications dispatched.")
                        .build());
    }

    /**
     * POST /api/v1/notifications/email
     */
    @Override
    @Transactional
    public NotificationResponse sendEmailNotification(DirectEmailRequest request) {
        log.info("REST: Sending direct email to {}", request.getRecipientEmail());
        emailService.sendDirectEmail(request);

        List<Notification> records = request.getBookingId() != null
                ? notificationRepository.findByBookingIdOrderByCreatedAtDesc(request.getBookingId())
                : notificationRepository.findByUserIdOrderByCreatedAtDesc(request.getUserId());

        return records.stream()
                .filter(n -> n.getNotificationType() == NotificationType.GENERAL)
                .findFirst()
                .map(this::toResponse)
                .orElse(NotificationResponse.builder()
                        .userId(request.getUserId())
                        .status("SENT")
                        .message("Email dispatched.")
                        .build());
    }

    /**
     * GET /api/v1/notifications/user/{userId}
     */
    @Override
    public List<NotificationResponse> getUserNotifications(Long userId) {
        return notificationRepository.findByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * GET /api/v1/notifications/booking/{bookingId}
     */
    @Override
    public List<NotificationResponse> getNotificationsByBooking(Long bookingId) {
        return notificationRepository.findByBookingIdOrderByCreatedAtDesc(bookingId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * POST /api/v1/notifications/{notificationId}/retry
     */
    @Override
    @Transactional
    public NotificationResponse retryFailedNotification(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found: " + notificationId));

        if (notification.getStatus() != NotificationStatus.FAILED) {
            throw new IllegalStateException("Only FAILED notifications can be retried. Current status: "
                    + notification.getStatus());
        }

        log.info("Retrying notification id={}, attempt #{}", notificationId, notification.getRetryCount() + 1);
        notification.incrementRetry();

        // Re-build a minimal event from stored data and re-dispatch
        BookingEventDTO event = BookingEventDTO.builder()
                .bookingId(notification.getBookingId())
                .userId(notification.getUserId())
                .passengerEmail(notification.getRecipient())
                .build();

        try {
            switch (notification.getNotificationType()) {
                case BOOKING_CONFIRMATION -> emailService.sendBookingConfirmationEmail(event);
                case BOOKING_CANCELLATION -> emailService.sendBookingCancellationEmail(event);
                default -> {
                    DirectEmailRequest req = DirectEmailRequest.builder()
                            .userId(notification.getUserId())
                            .bookingId(notification.getBookingId())
                            .recipientEmail(notification.getRecipient())
                            .subject("Notification Retry")
                            .message(notification.getMessage())
                            .build();
                    emailService.sendDirectEmail(req);
                }
            }
            notification.send();
        } catch (Exception e) {
            notification.markFailed(e.getMessage());
            log.error("Retry failed for notificationId={}: {}", notificationId, e.getMessage());
        }

        notificationRepository.save(notification);
        return toResponse(notification);
    }

    // ---------------------------------------------------------------
    // Called by ReminderScheduler (interface compliance)
    // ---------------------------------------------------------------

    @Override
    public void sendEmailNotification() {
        // Hook for scheduler — actual logic in EmailService
        log.debug("sendEmailNotification() stub called from scheduler");
    }

    @Override
    public void sendSMSNotification() {
        // Hook for scheduler — actual logic in SmsService
        log.debug("sendSMSNotification() stub called from scheduler");
    }

    @Override
    public void saveNotificationRecord(Notification notification) {
        notificationRepository.save(notification);
    }

    // ---------------------------------------------------------------
    // Private helpers
    // ---------------------------------------------------------------

    private void sendEmailNotification(BookingEventDTO event, BookingStatus status) {
        if (status == BookingStatus.CONFIRMED) {
            emailService.sendBookingConfirmationEmail(event);
        } else {
            emailService.sendBookingCancellationEmail(event);
        }
    }

    private void sendSMSNotification(BookingEventDTO event, BookingStatus status) {
        if (status == BookingStatus.CONFIRMED) {
            smsService.sendBookingConfirmationSms(event);
        } else {
            smsService.sendBookingCancellationSms(event);
        }
    }

    private NotificationResponse toResponse(Notification n) {
        return NotificationResponse.builder()
                .notificationId(n.getNotificationId())
                .bookingId(n.getBookingId())
                .userId(n.getUserId())
                .recipient(n.getRecipient())
                .message(n.getMessage())
                .status(n.getStatus().name())
                .notificationType(n.getNotificationType().name())
                .channel(n.getChannel().name())
                .sentAt(n.getSentAt() != null ? n.getSentAt().toString() : null)
                .retryCount(n.getRetryCount())
                .errorMessage(n.getErrorMessage())
                .build();
    }
}
