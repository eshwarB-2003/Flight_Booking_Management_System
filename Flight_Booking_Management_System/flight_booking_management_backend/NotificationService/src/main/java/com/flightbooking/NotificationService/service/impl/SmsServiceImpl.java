package com.flightbooking.NotificationService.service.impl;

import com.flightbooking.NotificationService.dto.BookingEventDTO;
import com.flightbooking.NotificationService.entity.SMSNotification;
import com.flightbooking.NotificationService.enums.NotificationChannel;
import com.flightbooking.NotificationService.enums.NotificationStatus;
import com.flightbooking.NotificationService.enums.NotificationType;
import com.flightbooking.NotificationService.repo.SMSNotificationRepository;
import com.flightbooking.NotificationService.service.SmsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SmsServiceImpl implements SmsService {
    private static final Logger log = LoggerFactory.getLogger(SmsServiceImpl.class);
    @Autowired
    private SMSNotificationRepository smsNotificationRepository;

    @Value("${notification.sms.enabled:false}")
    private boolean smsEnabled;

    // Twilio credentials — only used when smsEnabled = true
    @Value("${notification.sms.account-sid:not-configured}")
    private String twilioAccountSid;

    @Value("${notification.sms.auth-token:not-configured}")
    private String twilioAuthToken;

    @Value("${notification.sms.from-number:+0000000000}")
    private String twilioFromNumber;

    // ---------------------------------------------------------------
    // Booking Confirmation SMS
    // ---------------------------------------------------------------

    @Override
    public void sendBookingConfirmationSms(BookingEventDTO event) {
        if (event.getPassengerPhone() == null || event.getPassengerPhone().isBlank()) {
            log.warn("No phone number for userId={}, skipping SMS", event.getUserId());
            return;
        }

        SMSNotification sms = buildSms(
                event.getBookingId(),
                event.getUserId(),
                event.getPassengerPhone(),
                event.getPassengerName(),
                event.getFlightNumber(),
                event.getDepartureTime(),
                event.getBookingReference(),
                NotificationType.BOOKING_CONFIRMATION
        );

        String body = String.format(
                "Hi %s! Booking %s CONFIRMED. Flight %s departs %s. Have a great trip! – FlightBooking",
                event.getPassengerName(), event.getBookingReference(),
                event.getFlightNumber(), event.getDepartureTime()
        );
        sms.setMessage(body);

        dispatchSms(sms);
    }

    // ---------------------------------------------------------------
    // Booking Cancellation SMS
    // ---------------------------------------------------------------

    @Override
    public void sendBookingCancellationSms(BookingEventDTO event) {
        if (event.getPassengerPhone() == null || event.getPassengerPhone().isBlank()) {
            log.warn("No phone number for userId={}, skipping SMS", event.getUserId());
            return;
        }

        SMSNotification sms = buildSms(
                event.getBookingId(),
                event.getUserId(),
                event.getPassengerPhone(),
                event.getPassengerName(),
                event.getFlightNumber(),
                event.getDepartureTime(),
                event.getBookingReference(),
                NotificationType.BOOKING_CANCELLATION
        );

        String body = String.format(
                "Hi %s, booking %s has been CANCELLED. Refund will be processed in 5-7 days. – FlightBooking",
                event.getPassengerName(), event.getBookingReference()
        );
        sms.setMessage(body);

        dispatchSms(sms);
    }

    // ---------------------------------------------------------------
    // Check-In Reminder SMS
    // ---------------------------------------------------------------

    @Override
    public void sendCheckInReminderSms(Long bookingId, String phoneNumber,
                                        String passengerName, String flightNumber,
                                        String departureTime, String bookingReference) {
        if (phoneNumber == null || phoneNumber.isBlank()) return;

        SMSNotification sms = buildSms(
                bookingId, 0L, phoneNumber, passengerName,
                flightNumber, departureTime, bookingReference,
                NotificationType.CHECKIN_REMINDER
        );

        String body = String.format(
                "Reminder: Flight %s departs in 24hrs (%s). Check-in now! Ref: %s – FlightBooking",
                flightNumber, departureTime, bookingReference
        );
        sms.setMessage(body);

        dispatchSms(sms);
    }

    // ---------------------------------------------------------------
    // Internal helpers
    // ---------------------------------------------------------------

    private SMSNotification buildSms(Long bookingId, Long userId, String phone,
                                      String passengerName, String flightNumber,
                                      String departureTime, String bookingReference,
                                      NotificationType type) {
        SMSNotification sms = new SMSNotification();
        sms.setBookingId(bookingId);
        sms.setUserId(userId);
        sms.setRecipient(phone);
        sms.setPhoneNumber(phone);
        sms.setPassengerName(passengerName);
        sms.setFlightNumber(flightNumber);
        sms.setDepartureTime(departureTime);
        sms.setBookingReference(bookingReference);
        sms.setStatus(NotificationStatus.PENDING);
        sms.setNotificationType(type);
        sms.setChannel(NotificationChannel.SMS);
        return sms;
    }

    private void dispatchSms(SMSNotification sms) {
        try {
            if (smsEnabled) {
                sendViaTwilio(sms.getPhoneNumber(), sms.getMessage());
            } else {
                // Simulate successful send in dev/test mode
                log.info("[SMS SIMULATION] To: {} | Message: {}", sms.getPhoneNumber(), sms.getMessage());
            }
            sms.sendSMS();
        } catch (Exception e) {
            sms.markFailed(e.getMessage());
            log.error("Failed to send SMS to {}: {}", sms.getPhoneNumber(), e.getMessage());
        }
        smsNotificationRepository.save(sms);
    }

    /**
     * Twilio integration.
     * To activate: set notification.sms.enabled=true and supply real credentials.
     * Requires: com.twilio.sdk:twilio in pom.xml
     *
     * Uncomment below and add the Twilio dependency when ready:
     *
     * <dependency>
     *   <groupId>com.twilio.sdk</groupId>
     *   <artifactId>twilio</artifactId>
     *   <version>9.3.2</version>
     * </dependency>
     */
    private void sendViaTwilio(String toPhone, String body) {
        // Twilio.init(twilioAccountSid, twilioAuthToken);
        // Message.creator(new PhoneNumber(toPhone), new PhoneNumber(twilioFromNumber), body).create();
        log.info("Twilio send to {} (stub — uncomment Twilio SDK call)", toPhone);
    }
}
