package com.flightbooking.FlightInventoryService.DTO;

import com.flightbooking.FlightInventoryService.Entity.SeatStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SeatResponseDTO {

    private Long seatId;
    private String seatNumber;
    private String seatType;
    private SeatStatus seatStatus;
    private Double price;
    private Double finalPrice;
    private LocalDateTime lockTime;
    private Long scheduleId;

    public Long getSeatId() {
		return seatId;
	}

	public void setSeatId(Long seatId) {
		this.seatId = seatId;
	}

	public String getSeatNumber() {
		return seatNumber;
	}

	public void setSeatNumber(String seatNumber) {
		this.seatNumber = seatNumber;
	}

	public String getSeatType() {
		return seatType;
	}

	public void setSeatType(String seatType) {
		this.seatType = seatType;
	}

	public SeatStatus getSeatStatus() {
		return seatStatus;
	}

	public void setSeatStatus(SeatStatus seatStatus) {
		this.seatStatus = seatStatus;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getFinalPrice() {
		return finalPrice;
	}

	public void setFinalPrice(Double finalPrice) {
		this.finalPrice = finalPrice;
	}

	public LocalDateTime getLockTime() {
		return lockTime;
	}

	public void setLockTime(LocalDateTime lockTime) {
		this.lockTime = lockTime;
	}

	public Long getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(Long scheduleId) {
		this.scheduleId = scheduleId;
	}

	private SeatResponseDTO(Builder builder) {
        this.seatId = builder.seatId;
        this.seatNumber = builder.seatNumber;
        this.seatType = builder.seatType;
        this.seatStatus = builder.seatStatus;
        this.price = builder.price;
        this.finalPrice = builder.finalPrice;
        this.lockTime = builder.lockTime;
        this.scheduleId = builder.scheduleId;
    }

    public static class Builder {
        private Long seatId;
        private String seatNumber;
        private String seatType;
        private SeatStatus seatStatus;
        private Double price;
        private Double finalPrice;
        private LocalDateTime lockTime;
        private Long scheduleId;

        public Builder seatId(Long seatId) {
            this.seatId = seatId;
            return this;
        }

        public Builder seatNumber(String seatNumber) {
            this.seatNumber = seatNumber;
            return this;
        }

        public Builder seatType(String seatType) {
            this.seatType = seatType;
            return this;
        }

        public Builder seatStatus(SeatStatus seatStatus) {
            this.seatStatus = seatStatus;
            return this;
        }

        public Builder price(Double price) {
            this.price = price;
            return this;
        }

        public Builder finalPrice(Double finalPrice) {
            this.finalPrice = finalPrice;
            return this;
        }

        public Builder lockTime(LocalDateTime lockTime) {
            this.lockTime = lockTime;
            return this;
        }

        public Builder scheduleId(Long scheduleId) {
            this.scheduleId = scheduleId;
            return this;
        }

        public SeatResponseDTO build() {
            if (seatNumber == null || seatNumber.isBlank()) {
                throw new IllegalArgumentException("Seat number is required");
            }
            if (price != null && price < 0) {
                throw new IllegalArgumentException("Price cannot be negative");
            }
            return new SeatResponseDTO(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}