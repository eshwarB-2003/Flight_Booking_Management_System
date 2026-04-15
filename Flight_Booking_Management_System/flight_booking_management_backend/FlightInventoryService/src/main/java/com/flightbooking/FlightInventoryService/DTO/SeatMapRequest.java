package com.flightbooking.FlightInventoryService.DTO;

import lombok.Data;

@Data
    public class SeatMapRequest {
        private String aircraftId;
        private int rows;
        private int cols;
		public String getAircraftId() {
			return aircraftId;
		}
		public void setAircraftId(String aircraftId) {
			this.aircraftId = aircraftId;
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
        
        
    }