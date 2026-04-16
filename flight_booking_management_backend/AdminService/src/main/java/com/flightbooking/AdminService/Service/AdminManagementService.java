package com.flightbooking.AdminService.Service;


import com.flightbooking.AdminService.DTO.MessageResponse;
import com.flightbooking.AdminService.DTO.AuditLogResponse;
import com.flightbooking.AdminService.DTO.SystemReportResponse;
import com.flightbooking.AdminService.DTO.UserResponse;
import com.flightbooking.AdminService.Entity.AuditLog;
import com.flightbooking.AdminService.Entity.User;
import com.flightbooking.AdminService.Repository.AuditLogRepository;
import com.flightbooking.AdminService.Repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminManagementService {
	@Autowired
    private UserRepository userRepository;
	@Autowired
    private AuditLogRepository auditLogRepository;
	@Autowired
    private JdbcTemplate jdbcTemplate;

    public List<UserResponse> getAllUsers(Integer adminUserId) {
        recordAudit(adminUserId, "VIEW_ALL_USERS", "users", null, "Admin viewed all users");
        return userRepository.findAll().stream().map(this::mapToUserResponse).toList();
    }

    public UserResponse getUserById(Integer userId, Integer adminUserId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        recordAudit(adminUserId, "VIEW_USER", "users", userId, "Admin viewed user details");
        return mapToUserResponse(user);
    }

    public String activateUser(Integer userId, Integer adminUserId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setAccountStatus("ACTIVE");
        userRepository.save(user);

        recordAudit(adminUserId, "ACTIVATE_USER", "users", userId, "Admin activated user account");
        return "User activated successfully";
    }

    public String deactivateUser(Integer userId, Integer adminUserId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setAccountStatus("DEACTIVATED");
        userRepository.save(user);

        recordAudit(adminUserId, "DEACTIVATE_USER", "users", userId, "Admin deactivated user account");
        return "User deactivated successfully";
    }

    public List<AuditLogResponse> getAllAuditLogs(Integer adminUserId) {
        recordAudit(adminUserId, "VIEW_AUDIT_LOGS", "audit_log", null, "Admin viewed all audit logs");
        return auditLogRepository.findAllByOrderByTimestampDesc()
                .stream()
                .map(this::mapToAuditLogResponse)
                .toList();
    }

    public List<AuditLogResponse> getAuditLogsByUser(Integer userId, Integer adminUserId) {
        recordAudit(adminUserId, "VIEW_USER_AUDIT_LOGS", "audit_log", userId, "Admin viewed audit logs by user");
        return auditLogRepository.findByUserIdOrderByTimestampDesc(userId)
                .stream()
                .map(this::mapToAuditLogResponse)
                .toList();
    }

    public SystemReportResponse generateSystemReport(Integer adminUserId) {
        Long totalBookings = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM booking",
                Long.class
        );

        BigDecimal totalRevenue = jdbcTemplate.queryForObject(
                "SELECT COALESCE(SUM(total_amount), 0) FROM booking WHERE booking_status = 'CONFIRMED'",
                BigDecimal.class
        );

        Long activeFlights = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM flight WHERE status = 'SCHEDULED'",
                Long.class
        );

        recordAudit(adminUserId, "GENERATE_REPORT", "system_report", null, "Admin generated system report");

        return SystemReportResponse.builder()
                .totalBookings(totalBookings != null ? totalBookings : 0L)
                .totalRevenue(totalRevenue != null ? totalRevenue : BigDecimal.ZERO)
                .activeFlights(activeFlights != null ? activeFlights : 0L)
                .generatedAt(LocalDateTime.now())
                .build();
    }

    public MessageResponse systemHealth(Integer adminUserId) {
        recordAudit(adminUserId, "CHECK_SYSTEM_HEALTH", "system", null, "Admin checked system health");
        return MessageResponse.builder()
                .message("Admin Service is running")
                .build();
    }

    private void recordAudit(Integer userId, String action, String entityType, Integer entityId, String details) {
        AuditLog auditLog = AuditLog.builder()
                .userId(userId)
                .action(action)
                .entityType(entityType)
                .entityId(entityId)
                .details(details)
                .build();

        auditLogRepository.save(auditLog);
    }

    private UserResponse mapToUserResponse(User user) {
        return UserResponse.builder()
                .userId(user.getUserId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole())
                .accountStatus(user.getAccountStatus())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    private AuditLogResponse mapToAuditLogResponse(AuditLog log) {
        return AuditLogResponse.builder()
                .logId(log.getLogId())
                .userId(log.getUserId())
                .action(log.getAction())
                .entityType(log.getEntityType())
                .entityId(log.getEntityId())
                .timestamp(log.getTimestamp())
                .details(log.getDetails())
                .build();
    }
}
