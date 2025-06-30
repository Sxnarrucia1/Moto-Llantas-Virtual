/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.motollantas.MotoLlantasVirtual.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

/**
 *
 * @author esteb
 */
public class AdminMotorcycleDTO {

    @NotBlank(message = "La marca de la moto es obligatoria")
    private String brand;

    @NotBlank(message = "El modelo de la moto es obligatorio")
    private String modelName;

    @Min(value = 1900, message = "El año debe ser válido")
    private Integer year;

    @NotBlank(message = "El número de placa es obligatorio")
    private String licensePlate;

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public AdminMotorcycleDTO() {
    }

    public AdminMotorcycleDTO(String brand, String modelName, Integer year, String licensePlate) {
        this.brand = brand;
        this.modelName = modelName;
        this.year = year;
        this.licensePlate = licensePlate;
    }
    
}
