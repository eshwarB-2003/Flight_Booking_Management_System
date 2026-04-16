package com.flightbooking.FlightInventoryService.DTO;

import lombok.Data;

@Data
public class SeatMapResponseDTO {

    private Long seatMapId;
    private int rows;
    private int cols;
    private String aircraftId;

    public Long getSeatMapId() {
		return seatMapId;
	}

	public void setSeatMapId(Long seatMapId) {
		this.seatMapId = seatMapId;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getCols() {
		return cols;
	}

	public void setCols(int cols) {
		this.cols = cols;
	}

	public String getAircraftId() {
		return aircraftId;
	}

	public void setAircraftId(String aircraftId) {
		this.aircraftId = aircraftId;
	}

	private SeatMapResponseDTO(Builder builder) {
        this.seatMapId = builder.seatMapId;
        this.rows = builder.rows;
        this.cols = builder.cols;
        this.aircraftId = builder.aircraftId;
    }

    public static class Builder {
        private Long seatMapId;
        private int rows;
        private int cols;
        private String aircraftId;

        public Builder seatMapId(Long seatMapId) {
            this.seatMapId = seatMapId;
            return this;
        }

        public Builder rows(int rows) {
            this.rows = rows;
            return this;
        }

        public Builder cols(int cols) {
            this.cols = cols;
            return this;
        }

        public Builder aircraftId(String aircraftId) {
            this.aircraftId = aircraftId;
            return this;
        }

        public SeatMapResponseDTO build() {
            if (rows <= 0 || cols <= 0) {
                throw new IllegalArgumentException("Rows and columns must be > 0");
            }
            return new SeatMapResponseDTO(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}