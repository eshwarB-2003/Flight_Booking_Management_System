package com.flightbooking.AdminService.Repository;


import com.flightbooking.AdminService.Entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuditLogRepository extends JpaRepository<AuditLog, Integer> {
    List<AuditLog> findByUserIdOrderByTimestampDesc(Integer userId);
    List<AuditLog> findAllByOrderByTimestampDesc();
}