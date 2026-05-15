package com.flightbooking.FlightInventoryService.Factory;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component("uuidGenerator")
public class UUIDAircraftIdGenerator implements AircraftIdGenerator{
    @Override
    public String generateId() {
        return UUID.randomUUID().toString();
    }
}
