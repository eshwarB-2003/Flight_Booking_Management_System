package com.flightbooking.NotificationService.repo;

import com.flightbooking.NotificationService.entity.EmailNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmailNotificationRepository extends JpaRepository<EmailNotification, Long> {
    List<EmailNotification> findByBookingId(Long bookingId);
    List<EmailNotification> findByUserId(Long userId);
}
