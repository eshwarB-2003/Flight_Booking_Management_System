package com.flightbooking.AdminService.Controller;



import com.flightbooking.AdminService.DTO.*;
import com.flightbooking.AdminService.Security.JwtTokenService;
import com.flightbooking.AdminService.Service.AdminManagementService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
	@Autowired
    private AdminManagementService adminManagementService;
	@Autowired
    private JwtTokenService jwtTokenService;

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getAllUsers(HttpServletRequest request) {
        Integer adminUserId = extractAdminUserId(request);
        return ResponseEntity.ok(adminManagementService.getAllUsers(adminUserId));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Integer id, HttpServletRequest request) {
        Integer adminUserId = extractAdminUserId(request);
        return ResponseEntity.ok(adminManagementService.getUserById(id, adminUserId));
    }

    @PutMapping("/users/{id}/activate")
    public ResponseEntity<MessageResponse> activateUser(@PathVariable Integer id, HttpServletRequest request) {
        Integer adminUserId = extractAdminUserId(request);
        return ResponseEntity.ok(
                MessageResponse.builder()
                        .message(adminManagementService.activateUser(id, adminUserId))
                        .build()
        );
    }

    @PutMapping("/users/{id}/deactivate")
    public ResponseEntity<MessageResponse> deactivateUser(@PathVariable Integer id, HttpServletRequest request) {
        Integer adminUserId = extractAdminUserId(request);
        return ResponseEntity.ok(
                MessageResponse.builder()
                        .message(adminManagementService.deactivateUser(id, adminUserId))
                        .build()
        );
    }

    @GetMapping("/audit-logs")
    public ResponseEntity<List<AuditLogResponse>> getAllAuditLogs(HttpServletRequest request) {
        Integer adminUserId = extractAdminUserId(request);
        return ResponseEntity.ok(adminManagementService.getAllAuditLogs(adminUserId));
    }

    @GetMapping("/audit-logs/{userId}")
    public ResponseEntity<List<AuditLogResponse>> getAuditLogsByUser(@PathVariable Integer userId,
                                                                     HttpServletRequest request) {
        Integer adminUserId = extractAdminUserId(request);
        return ResponseEntity.ok(adminManagementService.getAuditLogsByUser(userId, adminUserId));
    }

    @GetMapping("/reports")
    public ResponseEntity<SystemReportResponse> generateReport(HttpServletRequest request) {
        Integer adminUserId = extractAdminUserId(request);
        return ResponseEntity.ok(adminManagementService.generateSystemReport(adminUserId));
    }

    @GetMapping("/system-health")
    public ResponseEntity<MessageResponse> systemHealth(HttpServletRequest request) {
        Integer adminUserId = extractAdminUserId(request);
        return ResponseEntity.ok(adminManagementService.systemHealth(adminUserId));
    }

    private Integer extractAdminUserId(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        String token = authHeader.substring(7);
        return jwtTokenService.extractUserId(token);
    }
}