package com.flightbooking.NotificationService.scheduler;

import com.flightbooking.NotificationService.entity.EmailNotification;
import com.flightbooking.NotificationService.entity.Notification;
import com.flightbooking.NotificationService.enums.NotificationStatus;
import com.flightbooking.NotificationService.enums.NotificationType;
import com.flightbooking.NotificationService.repo.EmailNotificationRepository;
import com.flightbooking.NotificationService.repo.NotificationRepository;
import com.flightbooking.NotificationService.service.EmailService;
import com.flightbooking.NotificationService.service.SmsService;
import lombok.RequiredArgsConstructor;
import com.flightbooking.NotificationService.dto.NotificationDTOs.DirectEmailRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ReminderScheduler {
	
	private static final Logger log = LoggerFactory.getLogger(ReminderScheduler.class);
	
	@Autowired
    private EmailNotificationRepository emailNotificationRepository;
	@Autowired
    private NotificationRepository notificationRepository;
	@Autowired
    private EmailService emailService;
	@Autowired
    private SmsService smsService;

    @Value("${notification.checkin.reminder-hours-before:24}")
    private int reminderHoursBefore;

    private static final int MAX_RETRY_ATTEMPTS = 3;

    // ---------------------------------------------------------------
    // 1. Check-in reminder – runs every hour
    // ---------------------------------------------------------------

    /**
     * Looks through confirmed email notification records that have a departure time
     * roughly reminderHoursBefore hours away, and sends a check-in reminder if not
     * already sent.
     *
     * Cron: every hour at :00
     */
    @Scheduled(cron = "0 0 * * * *")
    public void checkUpcomingFlights() {
        log.info("ReminderScheduler: checking upcoming flights for check-in reminders");

        List<EmailNotification> confirmedEmails = emailNotificationRepository
                .findAll()
                .stream()
                .filter(e -> e.getNotificationType() == NotificationType.BOOKING_CONFIRMATION
                          && e.getStatus() == NotificationStatus.SENT
                          && e.getDepartureTime() != null)
                .toList();

        int remindersSent = 0;

        for (EmailNotification email : confirmedEmails) {
            try {
                // Skip if we already sent a reminder for this booking
                if (notificationRepository.existsByBookingIdAndNotificationType(
                        email.getBookingId(), NotificationType.CHECKIN_REMINDER)) {
                    continue;
                }

                LocalDateTime departure = parseDeparture(email.getDepartureTime());
                if (departure == null) continue;

                LocalDateTime now = LocalDateTime.now();
                long hoursUntilDeparture = java.time.Duration.between(now, departure).toHours();

                if (hoursUntilDeparture >= 0 && hoursUntilDeparture <= reminderHoursBefore) {
                    sendCheckInReminder(email);
                    remindersSent++;
                }
            } catch (Exception e) {
                log.error("Error processing reminder for bookingId={}: {}",
                        email.getBookingId(), e.getMessage());
            }
        }

        log.info("ReminderScheduler: sent {} check-in reminders", remindersSent);
    }

    /**
     * Sends email (and optionally SMS) check-in reminder.
     */
    public void sendCheckInReminder(EmailNotification email) {
        log.info("Sending check-in reminder for bookingId={}, flight={}",
                email.getBookingId(), email.getFlightNumber());

        emailService.sendCheckInReminderEmail(
                email.getBookingId(),
                email.getRecipient(),
                email.getPassengerName(),
                email.getFlightNumber(),
                email.getDepartureTime(),
                email.getBookingReference()
        );
    }

    // ---------------------------------------------------------------
    // 2. Auto-retry failed notifications – runs every 15 minutes
    // ---------------------------------------------------------------

    @Scheduled(fixedDelay = 15 * 60 * 1000)
    public void retryFailedNotifications() {
        List<Notification> retryable = notificationRepository.findRetryableNotifications(MAX_RETRY_ATTEMPTS);

        if (retryable.isEmpty()) return;

        log.info("ReminderScheduler: found {} failed notifications to retry", retryable.size());

        for (Notification notification : retryable) {
            try {
                notification.incrementRetry();
                notificationRepository.save(notification);

                log.info("Auto-retrying notificationId={} (attempt {})",
                        notification.getNotificationId(), notification.getRetryCount());

                // Re-attempt delivery based on type
                if (notification instanceof EmailNotification emailNotif) {
                    retryEmailNotification(emailNotif);
                }

            } catch (Exception e) {
                notification.markFailed(e.getMessage());
                notificationRepository.save(notification);
                log.error("Auto-retry failed for notificationId={}: {}",
                        notification.getNotificationId(), e.getMessage());
            }
        }
    }

    private void retryEmailNotification(EmailNotification emailNotif) {
        DirectEmailRequest req = new DirectEmailRequest();
        req.setUserId(emailNotif.getUserId());
        req.setBookingId(emailNotif.getBookingId());
        req.setRecipientEmail(emailNotif.getRecipient());
        req.setSubject(emailNotif.getSubject() != null ? emailNotif.getSubject() : "Notification Retry");
        req.setMessage(emailNotif.getMessage());
        emailService.sendDirectEmail(req);
    }

    // ---------------------------------------------------------------
    // Helper
    // ---------------------------------------------------------------

    private LocalDateTime parseDeparture(String departureTime) {
        if (departureTime == null || departureTime.isBlank()) return null;
        try {
            // Try ISO format first: "2025-08-15T14:30:00"
            return LocalDateTime.parse(departureTime);
        } catch (Exception ex1) {
            try {
                // Friendly format: "15 Aug 2025 14:30"
                DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm");
                return LocalDateTime.parse(departureTime, fmt);
            } catch (Exception ex2) {
                log.warn("Cannot parse departure time '{}': {}", departureTime, ex2.getMessage());
                return null;
            }
        }
    }
}
