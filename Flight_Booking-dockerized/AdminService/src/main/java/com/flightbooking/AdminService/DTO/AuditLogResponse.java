package com.flightbooking.AdminService.DTO;



import java.time.LocalDateTime;

public class AuditLogResponse {

    private Integer logId;
    private Integer userId;
    private String action;
    private String entityType;
    private Integer entityId;
    private LocalDateTime timestamp;
    private String details;

    private AuditLogResponse(AuditLogResponseBuilder builder) {
        this.logId = builder.logId;
        this.userId = builder.userId;
        this.action = builder.action;
        this.entityType = builder.entityType;
        this.entityId = builder.entityId;
        this.timestamp = builder.timestamp;
        this.details = builder.details;
    }

    public AuditLogResponse() {}

    public static AuditLogResponseBuilder builder() {
        return new AuditLogResponseBuilder();
    }

    public static class AuditLogResponseBuilder {
        private Integer logId;
        private Integer userId;
        private String action;
        private String entityType;
        private Integer entityId;
        private LocalDateTime timestamp;
        private String details;

        public AuditLogResponseBuilder logId(Integer logId) {
            this.logId = logId;
            return this;
        }

        public AuditLogResponseBuilder userId(Integer userId) {
            this.userId = userId;
            return this;
        }

        public AuditLogResponseBuilder action(String action) {
            this.action = action;
            return this;
        }

        public AuditLogResponseBuilder entityType(String entityType) {
            this.entityType = entityType;
            return this;
        }

        public AuditLogResponseBuilder entityId(Integer entityId) {
            this.entityId = entityId;
            return this;
        }

        public AuditLogResponseBuilder timestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public AuditLogResponseBuilder details(String details) {
            this.details = details;
            return this;
        }

        public AuditLogResponse build() {
            return new AuditLogResponse(this);
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
