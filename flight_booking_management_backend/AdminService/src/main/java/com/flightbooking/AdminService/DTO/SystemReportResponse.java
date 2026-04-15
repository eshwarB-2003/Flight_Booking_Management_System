package com.flightbooking.AdminService.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class SystemReportResponse {

    private Long totalBookings;
    private BigDecimal totalRevenue;
    private Long activeFlights;
    private LocalDateTime generatedAt;

    private SystemReportResponse(SystemReportResponseBuilder builder) {
        this.totalBookings = builder.totalBookings;
        this.totalRevenue = builder.totalRevenue;
        this.activeFlights = builder.activeFlights;
        this.generatedAt = builder.generatedAt;
    }

    public SystemReportResponse() {}

    public static SystemReportResponseBuilder builder() {
        return new SystemReportResponseBuilder();
    }

    public static class SystemReportResponseBuilder {
        private Long totalBookings;
        private BigDecimal totalRevenue;
        private Long activeFlights;
        private LocalDateTime generatedAt;

        public SystemReportResponseBuilder totalBookings(Long totalBookings) {
            this.totalBookings = totalBookings;
            return this;
        }

        public SystemReportResponseBuilder totalRevenue(BigDecimal totalRevenue) {
            this.totalRevenue = totalRevenue;
            return this;
        }

        public SystemReportResponseBuilder activeFlights(Long activeFlights) {
            this.activeFlights = activeFlights;
            return this;
        }

        public SystemReportResponseBuilder generatedAt(LocalDateTime generatedAt) {
            this.generatedAt = generatedAt;
            return this;
        }

        public SystemReportResponse build() {
            return new SystemReportResponse(this);
        }
    }

    public Long getTotalBookings() { return totalBookings; }
    public void setTotalBookings(Long totalBookings) { this.totalBookings = totalBookings; }

    public BigDecimal getTotalRevenue() { return totalRevenue; }
    public void setTotalRevenue(BigDecimal totalRevenue) { this.totalRevenue = totalRevenue; }

    public Long getActiveFlights() { return activeFlights; }
    public void setActiveFlights(Long activeFlights) { this.activeFlights = activeFlights; }

    public LocalDateTime getGeneratedAt() { return generatedAt; }
    public void setGeneratedAt(LocalDateTime generatedAt) { this.generatedAt = generatedAt; }
}
