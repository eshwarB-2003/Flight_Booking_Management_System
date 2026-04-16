package com.flightbooking.FlightInventoryService.DTO;

import lombok.Data;

@Data
public class SeatRequestDTO {

    private Long scheduleId;
    private String seatNumber;
	public Long getScheduleId() {
		return scheduleId;
	}
	public void setScheduleId(Long scheduleId) {
		this.scheduleId = scheduleId;
	}
	public String getSeatNumber() {
		return seatNumber;
	}
	public void setSeatNumber(String seatNumber) {
		this.seatNumber = seatNumber;
	}
}
