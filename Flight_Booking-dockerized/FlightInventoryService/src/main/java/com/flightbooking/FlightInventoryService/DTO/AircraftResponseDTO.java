package com.flightbooking.FlightInventoryService.DTO;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class AircraftResponseDTO {

    private String aircraftId;
    private String model;
    private String manufacturer;
    private int totalCapacity;
    private LocalDateTime createdAt;
    private List<AircraftClassDTO> classes;
    private int rows;
    private int cols;

    public String getAircraftId() {
		return aircraftId;
	}

	public void setAircraftId(String aircraftId) {
		this.aircraftId = aircraftId;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public int getTotalCapacity() {
		return totalCapacity;
	}

	public void setTotalCapacity(int totalCapacity) {
		this.totalCapacity = totalCapacity;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public List<AircraftClassDTO> getClasses() {
		return classes;
	}

	public void setClasses(List<AircraftClassDTO> classes) {
		this.classes = classes;
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

	private AircraftResponseDTO(Builder builder) {
        this.aircraftId = builder.aircraftId;
        this.model = builder.model;
        this.manufacturer = builder.manufacturer;
        this.totalCapacity = builder.totalCapacity;
        this.createdAt = builder.createdAt;
        this.classes = builder.classes;
        this.rows = builder.rows;
        this.cols = builder.cols;
    }

    public static class Builder {
        private String aircraftId;
        private String model;
        private String manufacturer;
        private int totalCapacity;
        private LocalDateTime createdAt;
        private List<AircraftClassDTO> classes;
        private int rows;
        private int cols;

        public Builder aircraftId(String aircraftId) {
            this.aircraftId = aircraftId;
            return this;
        }

        public Builder model(String model) {
            this.model = model;
            return this;
        }

        public Builder manufacturer(String manufacturer) {
            this.manufacturer = manufacturer;
            return this;
        }

        public Builder totalCapacity(int totalCapacity) {
            this.totalCapacity = totalCapacity;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder classes(List<AircraftClassDTO> classes) {
            this.classes = classes;
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

        public AircraftResponseDTO build() {
            return new AircraftResponseDTO(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
