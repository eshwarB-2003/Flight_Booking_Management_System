package com.flightbooking.NotificationService.service.impl;

import com.flightbooking.NotificationService.dto.BookingEventDTO;
import com.flightbooking.NotificationService.dto.NotificationDTOs.DirectEmailRequest;
import com.flightbooking.NotificationService.entity.EmailNotification;
import com.flightbooking.NotificationService.enums.NotificationChannel;
import com.flightbooking.NotificationService.enums.NotificationStatus;
import com.flightbooking.NotificationService.enums.NotificationType;
import com.flightbooking.NotificationService.repo.EmailNotificationRepository;
import com.flightbooking.NotificationService.service.EmailService;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
	
	private static final Logger log = LoggerFactory.getLogger(EmailServiceImpl.class);
	@Autowired
    private JavaMailSender mailSender;
	@Autowired
    private EmailNotificationRepository emailNotificationRepository;

    @Value("${notification.email.from}")
    private String fromEmail;

    @Value("${notification.email.from-name}")
    private String fromName;

    // ---------------------------------------------------------------
    // Booking Confirmation Email
    // ---------------------------------------------------------------

    @Override
    public void sendBookingConfirmationEmail(BookingEventDTO event) {
        String seatList = event.getSeatNumbers() != null
                ? String.join(", ", event.getSeatNumbers())
                : "N/A";

        String subject = "Booking Confirmed – " + event.getBookingReference();
        String htmlBody = buildConfirmationEmailHtml(
                event.getPassengerName(),
                event.getBookingReference(),
                event.getFlightNumber(),
                event.getOrigin(),
                event.getDestination(),
                event.getDepartureTime(),
                event.getArrivalTime(),
                seatList,
                event.getTotalAmount()
        );

        EmailNotification emailNotification = new EmailNotification();
        emailNotification.setBookingId(event.getBookingId());
        emailNotification.setUserId(event.getUserId());
        emailNotification.setRecipient(event.getPassengerEmail());
        emailNotification.setSubject(subject);
        emailNotification.setMessage(htmlBody);
        emailNotification.setStatus(NotificationStatus.PENDING);
        emailNotification.setNotificationType(NotificationType.BOOKING_CONFIRMATION);
        emailNotification.setChannel(NotificationChannel.EMAIL);
        emailNotification.setBookingReference(event.getBookingReference());
        emailNotification.setFlightNumber(event.getFlightNumber());
        emailNotification.setOrigin(event.getOrigin());
        emailNotification.setDestination(event.getDestination());
        emailNotification.setDepartureTime(event.getDepartureTime());
        emailNotification.setArrivalTime(event.getArrivalTime());
        emailNotification.setSeatNumbers(seatList);
        emailNotification.setTotalAmount(event.getTotalAmount());
        emailNotification.setPassengerName(event.getPassengerName());

        try {
            sendHtmlEmail(event.getPassengerEmail(), subject, htmlBody);
            emailNotification.sendEmail();
            log.info("Booking confirmation email sent to {}", event.getPassengerEmail());
        } catch (Exception e) {
            emailNotification.markFailed(e.getMessage());
            log.error("Failed to send confirmation email to {}: {}", event.getPassengerEmail(), e.getMessage());
        }

        emailNotificationRepository.save(emailNotification);
    }

    // ---------------------------------------------------------------
    // Booking Cancellation Email
    // ---------------------------------------------------------------

    @Override
    public void sendBookingCancellationEmail(BookingEventDTO event) {
        String subject = "Booking Cancelled – " + event.getBookingReference();
        String htmlBody = buildCancellationEmailHtml(
                event.getPassengerName(),
                event.getBookingReference(),
                event.getFlightNumber(),
                event.getDepartureTime(),
                event.getTotalAmount()
        );

        EmailNotification emailNotification = new EmailNotification();
        emailNotification.setBookingId(event.getBookingId());
        emailNotification.setUserId(event.getUserId());
        emailNotification.setRecipient(event.getPassengerEmail());
        emailNotification.setSubject(subject);
        emailNotification.setMessage(htmlBody);
        emailNotification.setStatus(NotificationStatus.PENDING);
        emailNotification.setNotificationType(NotificationType.BOOKING_CANCELLATION);
        emailNotification.setChannel(NotificationChannel.EMAIL);
        emailNotification.setBookingReference(event.getBookingReference());
        emailNotification.setFlightNumber(event.getFlightNumber());
        emailNotification.setDepartureTime(event.getDepartureTime());
        emailNotification.setPassengerName(event.getPassengerName());

        try {
            sendHtmlEmail(event.getPassengerEmail(), subject, htmlBody);
            emailNotification.sendEmail();
            log.info("Booking cancellation email sent to {}", event.getPassengerEmail());
        } catch (Exception e) {
            emailNotification.markFailed(e.getMessage());
            log.error("Failed to send cancellation email to {}: {}", event.getPassengerEmail(), e.getMessage());
        }

        emailNotificationRepository.save(emailNotification);
    }

    // ---------------------------------------------------------------
    // Direct / Custom Email  – POST /api/v1/notifications/email
    // ---------------------------------------------------------------

    @Override
    public void sendDirectEmail(DirectEmailRequest request) {
        EmailNotification emailNotification = new EmailNotification();
        emailNotification.setBookingId(request.getBookingId() != null ? request.getBookingId() : 0L);
        emailNotification.setUserId(request.getUserId());
        emailNotification.setRecipient(request.getRecipientEmail());
        emailNotification.setSubject(request.getSubject());
        emailNotification.setMessage(request.getMessage());
        emailNotification.setStatus(NotificationStatus.PENDING);
        emailNotification.setNotificationType(NotificationType.GENERAL);
        emailNotification.setChannel(NotificationChannel.EMAIL);

        try {
            sendHtmlEmail(request.getRecipientEmail(), request.getSubject(), request.getMessage());
            emailNotification.sendEmail();
            log.info("Direct email sent to {}", request.getRecipientEmail());
        } catch (Exception e) {
            emailNotification.markFailed(e.getMessage());
            log.error("Failed to send direct email to {}: {}", request.getRecipientEmail(), e.getMessage());
        }

        emailNotificationRepository.save(emailNotification);
    }

    // ---------------------------------------------------------------
    // Check-In Reminder Email
    // ---------------------------------------------------------------

    @Override
    public void sendCheckInReminderEmail(Long bookingId, String recipientEmail,
                                         String passengerName, String flightNumber,
                                         String departureTime, String bookingReference) {
        String subject = "Check-In Reminder – Flight " + flightNumber;
        String htmlBody = buildReminderEmailHtml(passengerName, bookingReference, flightNumber, departureTime);

        EmailNotification emailNotification = new EmailNotification();
        emailNotification.setBookingId(bookingId);
        emailNotification.setUserId(0L); // populated by scheduler, userId may not be available here
        emailNotification.setRecipient(recipientEmail);
        emailNotification.setSubject(subject);
        emailNotification.setMessage(htmlBody);
        emailNotification.setStatus(NotificationStatus.PENDING);
        emailNotification.setNotificationType(NotificationType.CHECKIN_REMINDER);
        emailNotification.setChannel(NotificationChannel.EMAIL);
        emailNotification.setBookingReference(bookingReference);
        emailNotification.setFlightNumber(flightNumber);
        emailNotification.setDepartureTime(departureTime);
        emailNotification.setPassengerName(passengerName);

        try {
            sendHtmlEmail(recipientEmail, subject, htmlBody);
            emailNotification.sendEmail();
            log.info("Check-in reminder sent to {} for flight {}", recipientEmail, flightNumber);
        } catch (Exception e) {
            emailNotification.markFailed(e.getMessage());
            log.error("Failed to send reminder to {}: {}", recipientEmail, e.getMessage());
        }

        emailNotificationRepository.save(emailNotification);
    }

    // ---------------------------------------------------------------
    // Internal helper – send actual MIME email
    // ---------------------------------------------------------------

    private void sendHtmlEmail(String to, String subject, String htmlBody) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setFrom(fromEmail, fromName);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, true);
        mailSender.send(message);
    }

    // ---------------------------------------------------------------
    // HTML templates
    // ---------------------------------------------------------------

    private String buildConfirmationEmailHtml(String name, String ref, String flight,
                                               String origin, String destination,
                                               String departure, String arrival,
                                               String seats, Double amount) {
        return "<!DOCTYPE html><html><body style='font-family:Arial,sans-serif;color:#333;'>"
                + "<div style='max-width:600px;margin:auto;border:1px solid #ddd;border-radius:8px;overflow:hidden;'>"
                + "<div style='background:#1a73e8;padding:24px;text-align:center;'>"
                + "<h1 style='color:#fff;margin:0;'>✈ Booking Confirmed</h1></div>"
                + "<div style='padding:24px;'>"
                + "<p>Dear <strong>" + name + "</strong>,</p>"
                + "<p>Your booking has been confirmed. Here are your details:</p>"
                + "<table style='width:100%;border-collapse:collapse;margin-top:12px;'>"
                + row("Booking Reference", "<strong style='color:#1a73e8;'>" + ref + "</strong>")
                + row("Flight", flight)
                + row("Route", origin + " → " + destination)
                + row("Departure", departure)
                + row("Arrival", arrival)
                + row("Seats", seats)
                + row("Total Amount", "€" + String.format("%.2f", amount != null ? amount : 0.0))
                + "</table>"
                + "<p style='margin-top:24px;'>Online check-in opens 24 hours before departure.</p>"
                + "<p>Thank you for choosing Flight Booking!</p>"
                + "</div>"
                + "<div style='background:#f5f5f5;padding:12px;text-align:center;font-size:12px;color:#999;'>"
                + "© 2025 Flight Booking Management System</div>"
                + "</div></body></html>";
    }

    private String buildCancellationEmailHtml(String name, String ref, String flight,
                                               String departure, Double amount) {
        return "<!DOCTYPE html><html><body style='font-family:Arial,sans-serif;color:#333;'>"
                + "<div style='max-width:600px;margin:auto;border:1px solid #ddd;border-radius:8px;overflow:hidden;'>"
                + "<div style='background:#d93025;padding:24px;text-align:center;'>"
                + "<h1 style='color:#fff;margin:0;'>Booking Cancelled</h1></div>"
                + "<div style='padding:24px;'>"
                + "<p>Dear <strong>" + name + "</strong>,</p>"
                + "<p>Your booking has been cancelled. Details below:</p>"
                + "<table style='width:100%;border-collapse:collapse;margin-top:12px;'>"
                + row("Booking Reference", ref)
                + row("Flight", flight)
                + row("Departure", departure)
                + row("Refund Amount", "€" + String.format("%.2f", amount != null ? amount : 0.0))
                + "</table>"
                + "<p style='margin-top:24px;'>Refunds are processed within 5–7 business days.</p>"
                + "</div>"
                + "<div style='background:#f5f5f5;padding:12px;text-align:center;font-size:12px;color:#999;'>"
                + "© 2025 Flight Booking Management System</div>"
                + "</div></body></html>";
    }

    private String buildReminderEmailHtml(String name, String ref, String flight, String departure) {
        return "<!DOCTYPE html><html><body style='font-family:Arial,sans-serif;color:#333;'>"
                + "<div style='max-width:600px;margin:auto;border:1px solid #ddd;border-radius:8px;overflow:hidden;'>"
                + "<div style='background:#f9a825;padding:24px;text-align:center;'>"
                + "<h1 style='color:#fff;margin:0;'>⏰ Check-In Reminder</h1></div>"
                + "<div style='padding:24px;'>"
                + "<p>Dear <strong>" + name + "</strong>,</p>"
                + "<p>Your flight is departing in <strong>24 hours</strong>. Time to check in!</p>"
                + "<table style='width:100%;border-collapse:collapse;margin-top:12px;'>"
                + row("Booking Reference", ref)
                + row("Flight", flight)
                + row("Departure", departure)
                + "</table>"
                + "<p style='margin-top:24px;'>Please check in online or at the airport desk.</p>"
                + "</div>"
                + "<div style='background:#f5f5f5;padding:12px;text-align:center;font-size:12px;color:#999;'>"
                + "© 2025 Flight Booking Management System</div>"
                + "</div></body></html>";
    }

    private String row(String label, String value) {
        return "<tr><td style='padding:8px;border-bottom:1px solid #eee;color:#666;width:40%;'>" + label
                + "</td><td style='padding:8px;border-bottom:1px solid #eee;'>" + value + "</td></tr>";
    }
}
