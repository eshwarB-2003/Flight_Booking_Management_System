package com.flightbooking.AdminService.DTO;



import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLogResponse {
    private Integer logId;
    private Integer userId;
    private String action;
    private String entityType;
    private Integer entityId;
    private LocalDateTime timestamp;
    private String details;
}
