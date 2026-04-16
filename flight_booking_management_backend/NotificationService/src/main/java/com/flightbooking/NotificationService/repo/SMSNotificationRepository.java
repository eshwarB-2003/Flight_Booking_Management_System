package com.flightbooking.NotificationService.repo;

import com.flightbooking.NotificationService.entity.SMSNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SMSNotificationRepository extends JpaRepository<SMSNotification, Long> {
    List<SMSNotification> findByBookingId(Long bookingId);
    List<SMSNotification> findByUserId(Long userId);
}
