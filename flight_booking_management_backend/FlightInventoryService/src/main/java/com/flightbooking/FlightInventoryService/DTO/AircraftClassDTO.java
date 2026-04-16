package com.flightbooking.FlightInventoryService.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AircraftClassDTO {

    @NotBlank
    private String className;

    @Min(1)
    private Integer seatCount;

    private Double priceMultiplier;

    private String status;

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Integer getSeatCount() {
		return seatCount;
	}

	public void setSeatCount(Integer seatCount) {
		this.seatCount = seatCount;
	}

	public Double getPriceMultiplier() {
		return priceMultiplier;
	}

	public void setPriceMultiplier(Double priceMultiplier) {
		this.priceMultiplier = priceMultiplier;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
