package com.flightbooking.NotificationService.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class NotificationDTOs {


    public static class BookingConfirmationRequest {
        @NotNull(message = "Booking ID is required")
        private Long bookingId;
        @NotNull(message = "User ID is required")
        private Long userId;
        @NotBlank(message = "Booking reference is required")
        private String bookingReference;
        @NotBlank(message = "Passenger name is required")
        private String passengerName;
        @Email(message = "Valid email is required")
        @NotBlank(message = "Email is required")
        private String passengerEmail;
        private String passengerPhone;
        @NotBlank(message = "Flight number is required")
        private String flightNumber;
        private String origin;
        private String destination;
        private String departureTime;
        private String arrivalTime;
        private String seatNumbers;
        private Double totalAmount;

        public Long getBookingId() { return bookingId; }
        public void setBookingId(Long bookingId) { this.bookingId = bookingId; }
        public Long getUserId() { return userId; }
        public void setUserId(Long userId) { this.userId = userId; }
        public String getBookingReference() { return bookingReference; }
        public void setBookingReference(String bookingReference) { this.bookingReference = bookingReference; }
        public String getPassengerName() { return passengerName; }
        public void setPassengerName(String passengerName) { this.passengerName = passengerName; }
        public String getPassengerEmail() { return passengerEmail; }
        public void setPassengerEmail(String passengerEmail) { this.passengerEmail = passengerEmail; }
        public String getPassengerPhone() { return passengerPhone; }
        public void setPassengerPhone(String passengerPhone) { this.passengerPhone = passengerPhone; }
        public String getFlightNumber() { return flightNumber; }
        public void setFlightNumber(String flightNumber) { this.flightNumber = flightNumber; }
        public String getOrigin() { return origin; }
        public void setOrigin(String origin) { this.origin = origin; }
        public String getDestination() { return destination; }
        public void setDestination(String destination) { this.destination = destination; }
        public String getDepartureTime() { return departureTime; }
        public void setDepartureTime(String departureTime) { this.departureTime = departureTime; }
        public String getArrivalTime() { return arrivalTime; }
        public void setArrivalTime(String arrivalTime) { this.arrivalTime = arrivalTime; }
        public String getSeatNumbers() { return seatNumbers; }
        public void setSeatNumbers(String seatNumbers) { this.seatNumbers = seatNumbers; }
        public Double getTotalAmount() { return totalAmount; }
        public void setTotalAmount(Double totalAmount) { this.totalAmount = totalAmount; }
    }

    public static class BookingCancellationRequest {
        @NotNull(message = "Booking ID is required")
        private Long bookingId;
        @NotNull(message = "User ID is required")
        private Long userId;
        @NotBlank(message = "Booking reference is required")
        private String bookingReference;
        @NotBlank(message = "Passenger name is required")
        private String passengerName;
        @Email(message = "Valid email is required")
        @NotBlank(message = "Email is required")
        private String passengerEmail;
        private String passengerPhone;
        private String flightNumber;
        private String departureTime;
        private Double refundAmount;
        private String cancellationReason;

        public Long getBookingId() { return bookingId; }
        public void setBookingId(Long bookingId) { this.bookingId = bookingId; }
        public Long getUserId() { return userId; }
        public void setUserId(Long userId) { this.userId = userId; }
        public String getBookingReference() { return bookingReference; }
        public void setBookingReference(String bookingReference) { this.bookingReference = bookingReference; }
        public String getPassengerName() { return passengerName; }
        public void setPassengerName(String passengerName) { this.passengerName = passengerName; }
        public String getPassengerEmail() { return passengerEmail; }
        public void setPassengerEmail(String passengerEmail) { this.passengerEmail = passengerEmail; }
        public String getPassengerPhone() { return passengerPhone; }
        public void setPassengerPhone(String passengerPhone) { this.passengerPhone = passengerPhone; }
        public String getFlightNumber() { return flightNumber; }
        public void setFlightNumber(String flightNumber) { this.flightNumber = flightNumber; }
        public String getDepartureTime() { return departureTime; }
        public void setDepartureTime(String departureTime) { this.departureTime = departureTime; }
        public Double getRefundAmount() { return refundAmount; }
        public void setRefundAmount(Double refundAmount) { this.refundAmount = refundAmount; }
        public String getCancellationReason() { return cancellationReason; }
        public void setCancellationReason(String cancellationReason) { this.cancellationReason = cancellationReason; }
    }


    public static class DirectEmailRequest {
        @NotNull(message = "User ID is required")
        private Long userId;
        private Long bookingId;
        @Email(message = "Valid email is required")
        @NotBlank(message = "Recipient email is required")
        private String recipientEmail;
        @NotBlank(message = "Subject is required")
        private String subject;
        @NotBlank(message = "Message body is required")
        private String message;

        public Long getUserId() { return userId; }
        public void setUserId(Long userId) { this.userId = userId; }
        public Long getBookingId() { return bookingId; }
        public void setBookingId(Long bookingId) { this.bookingId = bookingId; }
        public String getRecipientEmail() { return recipientEmail; }
        public void setRecipientEmail(String recipientEmail) { this.recipientEmail = recipientEmail; }
        public String getSubject() { return subject; }
        public void setSubject(String subject) { this.subject = subject; }
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }


        public static DirectEmailRequestBuilder builder() { return new DirectEmailRequestBuilder(); }

        public static class DirectEmailRequestBuilder {
            private Long userId;
            private Long bookingId;
            private String recipientEmail;
            private String subject;
            private String message;

            public DirectEmailRequestBuilder userId(Long userId) { this.userId = userId; return this; }
            public DirectEmailRequestBuilder bookingId(Long bookingId) { this.bookingId = bookingId; return this; }
            public DirectEmailRequestBuilder recipientEmail(String recipientEmail) { this.recipientEmail = recipientEmail; return this; }
            public DirectEmailRequestBuilder subject(String subject) { this.subject = subject; return this; }
            public DirectEmailRequestBuilder message(String message) { this.message = message; return this; }

            public DirectEmailRequest build() {
                DirectEmailRequest req = new DirectEmailRequest();
                req.setUserId(userId);
                req.setBookingId(bookingId);
                req.setRecipientEmail(recipientEmail);
                req.setSubject(subject);
                req.setMessage(message);
                return req;
            }
        }
    }


    public static class NotificationResponse {
        private Long notificationId;
        private Long bookingId;
        private Long userId;
        private String recipient;
        private String status;
        private String notificationType;
        private String channel;
        private String sentAt;
        private int retryCount;
        private String errorMessage;

        public Long getNotificationId() { return notificationId; }
        public void setNotificationId(Long notificationId) { this.notificationId = notificationId; }
        public Long getBookingId() { return bookingId; }
        public void setBookingId(Long bookingId) { this.bookingId = bookingId; }
        public Long getUserId() { return userId; }
        public void setUserId(Long userId) { this.userId = userId; }
        public String getRecipient() { return recipient; }
        public void setRecipient(String recipient) { this.recipient = recipient; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        public String getNotificationType() { return notificationType; }
        public void setNotificationType(String notificationType) { this.notificationType = notificationType; }
        public String getChannel() { return channel; }
        public void setChannel(String channel) { this.channel = channel; }
        public String getSentAt() { return sentAt; }
        public void setSentAt(String sentAt) { this.sentAt = sentAt; }
        public int getRetryCount() { return retryCount; }
        public void setRetryCount(int retryCount) { this.retryCount = retryCount; }
        public String getErrorMessage() { return errorMessage; }
        public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }


        public static NotificationResponseBuilder builder() { return new NotificationResponseBuilder(); }

        public static class NotificationResponseBuilder {
            private Long notificationId;
            private Long bookingId;
            private Long userId;
            private String recipient;
            private String status;
            private String notificationType;
            private String channel;
            private String sentAt;
            private int retryCount;
            private String errorMessage;

            public NotificationResponseBuilder notificationId(Long notificationId) { this.notificationId = notificationId; return this; }
            public NotificationResponseBuilder bookingId(Long bookingId) { this.bookingId = bookingId; return this; }
            public NotificationResponseBuilder userId(Long userId) { this.userId = userId; return this; }
            public NotificationResponseBuilder recipient(String recipient) { this.recipient = recipient; return this; }
            public NotificationResponseBuilder status(String status) { this.status = status; return this; }
            public NotificationResponseBuilder notificationType(String notificationType) { this.notificationType = notificationType; return this; }
            public NotificationResponseBuilder channel(String channel) { this.channel = channel; return this; }
            public NotificationResponseBuilder sentAt(String sentAt) { this.sentAt = sentAt; return this; }
            public NotificationResponseBuilder retryCount(int retryCount) { this.retryCount = retryCount; return this; }
            public NotificationResponseBuilder errorMessage(String errorMessage) { this.errorMessage = errorMessage; return this; }

            public NotificationResponse build() {
                NotificationResponse r = new NotificationResponse();
                r.setNotificationId(notificationId);
                r.setBookingId(bookingId);
                r.setUserId(userId);
                r.setRecipient(recipient);
                r.setStatus(status);
                r.setNotificationType(notificationType);
                r.setChannel(channel);
                r.setSentAt(sentAt);
                r.setRetryCount(retryCount);
                r.setErrorMessage(errorMessage);
                return r;
            }
        }
    }

    public static class ApiResponse<T> {
        private boolean success;
        private String message;
        private T data;

        public boolean isSuccess() { return success; }
        public void setSuccess(boolean success) { this.success = success; }
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
        public T getData() { return data; }
        public void setData(T data) { this.data = data; }

        public ApiResponse(boolean success, String message, T data) {
            this.success = success;
            this.message = message;
            this.data = data;
        }

        public static <T> ApiResponse<T> success(String message, T data) {
            return new ApiResponse<>(true, message, data);
        }

        public static <T> ApiResponse<T> error(String message) {
            return new ApiResponse<>(false, message, null);
        }

        public static <T> ApiResponseBuilder<T> builder() {
            return new ApiResponseBuilder<>();
        }

        public static class ApiResponseBuilder<T> {
            private boolean success;
            private String message;
            private T data;

            public ApiResponseBuilder<T> success(boolean success) { this.success = success; return this; }
            public ApiResponseBuilder<T> message(String message) { this.message = message; return this; }
            public ApiResponseBuilder<T> data(T data) { this.data = data; return this; }

            public ApiResponse<T> build() {
                return new ApiResponse<>(success, message, data);
            }
        }
    }
}
