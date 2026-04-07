package com.flightbooking.FlightInventoryService.Entity;

import jakarta.persistence.Table;

    public enum FlightStatus {
        SCHEDULED,
        ACTIVE,
        DELAYED,
        CANCELLED,
        COMPLETED
    }
