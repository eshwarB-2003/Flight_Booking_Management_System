package com.flightbooking.NotificationService.repo;

import com.flightbooking.NotificationService.entity.Notification;
import com.flightbooking.NotificationService.enums.NotificationStatus;
import com.flightbooking.NotificationService.enums.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    // GET /api/v1/notifications/user/{userId}
    List<Notification> findByUserIdOrderByCreatedAtDesc(Long userId);

    // GET /api/v1/notifications/booking/{bookingId}
    List<Notification> findByBookingIdOrderByCreatedAtDesc(Long bookingId);

    // Find all failed notifications (for retry scheduling)
    List<Notification> findByStatus(NotificationStatus status);

    // Find failed notifications with retry count below threshold
    @Query("SELECT n FROM Notification n WHERE n.status = 'FAILED' AND n.retryCount < :maxRetries")
    List<Notification> findRetryableNotifications(@Param("maxRetries") int maxRetries);

    // Find by type (e.g. get all checkin reminders)
    List<Notification> findByNotificationType(NotificationType type);

    // Exists check to prevent duplicate notifications
    boolean existsByBookingIdAndNotificationType(Long bookingId, NotificationType type);
}
