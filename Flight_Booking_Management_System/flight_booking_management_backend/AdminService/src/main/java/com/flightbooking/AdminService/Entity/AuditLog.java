package com.flightbooking.AdminService.Entity;



import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "audit_log")
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

    // Private constructor for builder
    private AuditLog(AuditLogBuilder builder) {
        this.logId = builder.logId;
        this.userId = builder.userId;
        this.action = builder.action;
        this.entityType = builder.entityType;
        this.entityId = builder.entityId;
        this.timestamp = builder.timestamp;
        this.details = builder.details;
    }

    // Default constructor (required by JPA)
    public AuditLog() {}

    // Static builder method
    public static AuditLogBuilder builder() {
        return new AuditLogBuilder();
    }

    // Builder class
    public static class AuditLogBuilder {
        private Integer logId;
        private Integer userId;
        private String action;
        private String entityType;
        private Integer entityId;
        private LocalDateTime timestamp;
        private String details;

        public AuditLogBuilder logId(Integer logId) {
            this.logId = logId;
            return this;
        }

        public AuditLogBuilder userId(Integer userId) {
            this.userId = userId;
            return this;
        }

        public AuditLogBuilder action(String action) {
            this.action = action;
            return this;
        }

        public AuditLogBuilder entityType(String entityType) {
            this.entityType = entityType;
            return this;
        }

        public AuditLogBuilder entityId(Integer entityId) {
            this.entityId = entityId;
            return this;
        }

        public AuditLogBuilder timestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public AuditLogBuilder details(String details) {
            this.details = details;
            return this;
        }

        public AuditLog build() {
            return new AuditLog(this);
        }
    }

    public Integer getLogId() { return logId; }
    public void setLogId(Integer logId) { this.logId = logId; }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }

    public String getEntityType() { return entityType; }
    public void setEntityType(String entityType) { this.entityType = entityType; }

    public Integer getEntityId() { return entityId; }
    public void setEntityId(Integer entityId) { this.entityId = entityId; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }
}
