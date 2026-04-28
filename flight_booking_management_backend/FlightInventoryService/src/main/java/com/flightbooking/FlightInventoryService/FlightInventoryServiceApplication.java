package com.flightbooking.FlightInventoryService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FlightInventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlightInventoryServiceApplication.class, args);
	}

}
