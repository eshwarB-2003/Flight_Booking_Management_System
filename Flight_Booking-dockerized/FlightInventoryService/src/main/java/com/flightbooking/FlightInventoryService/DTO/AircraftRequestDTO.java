package com.flightbooking.FlightInventoryService.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class AircraftRequestDTO {

    private String idType;
    @NotBlank
    private String model;

    @NotBlank
    private String manufacturer;

    @NotNull
    @Min(1)
    private int totalCapacity;
@NotNull
    private List<AircraftClassDTO> classes;
public String getIdType() {
	return idType;
}
public void setIdType(String idType) {
	this.idType = idType;
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
public List<AircraftClassDTO> getClasses() {
	return classes;
}
public void setClasses(List<AircraftClassDTO> classes) {
	this.classes = classes;
}

}
