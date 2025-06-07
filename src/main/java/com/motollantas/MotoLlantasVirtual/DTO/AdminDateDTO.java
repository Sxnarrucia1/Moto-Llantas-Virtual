/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.motollantas.MotoLlantasVirtual.DTO;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 *
 * @author esteb
 */
public class AdminDateDTO {

    @NotBlank(message = "El nombre del cliente es obligatorio")
    private String fullName;
    
    @NotBlank(message = "La identificación del cliente es obligatorio")
    private String identification;
    
    @NotBlank(message = "La marca de la motocicleta es obligatorio")
    private String brand;
    
    @NotBlank(message = "El modelo de la motocicleta es obligatorio")
    private String modelName;
    
    @Min(value = 1900, message = "El año debe ser válido")
    private int year;
    
    @NotBlank(message = "El número de placa de la motocicleta es obligatorio")
    private String licensePlate;
    
    
    @NotNull(message = "La fecha de la cita es obligatoria")
    @Future(message = "La fecha debe ser en el futuro")
    private LocalDateTime appointmentDate;
    
    
    private Long serviceTypeId;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public LocalDateTime getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDateTime appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public Long getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(Long serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }
}
