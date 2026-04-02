package com.flightbooking.AdminService.Entity;



import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "audit_log")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    private Integer logId;

    @Column(name = "user_id")
    private Integer userId;

    private String action;

    @Column(name = "entity_type")
    private String entityType;

    @Column(name = "entity_id")
    private Integer entityId;

    @Column(insertable = false, updatable = false)
    private LocalDateTime timestamp;

    private String details;
}
