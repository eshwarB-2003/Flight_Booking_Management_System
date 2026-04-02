package com.flightbooking.AdminService.DTO;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SystemReportResponse {
    private Long totalBookings;
    private BigDecimal totalRevenue;
    private Long activeFlights;
    private LocalDateTime generatedAt;
}
