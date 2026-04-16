package com.flightbooking.FlightInventoryService.Factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class AircraftIdFactory {

    @Autowired
    private Map<String, AircraftIdGenerator> generators;

    public AircraftIdGenerator getGenerator(String type) {
        return generators.getOrDefault(type, generators.get("uuidGenerator"));
    }
}
