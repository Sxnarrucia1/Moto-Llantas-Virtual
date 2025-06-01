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
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author esteb
 */
@Data
@NoArgsConstructor
public class ClientDateDTO {

    @NotBlank(message = "El nombre del cliente es obligatorio")
    private String clientName;

    @NotNull
    private String identification;

    @NotBlank(message = "La marca de la moto es obligatorio")
    private String brand;

    @Min(value = 1900, message = "El año debe ser valido")
    private int year;

    @NotBlank(message = "El modelo de la moto es obligatorio")
    private String modelName;

    @NotBlank(message = "El número de placa de la moto es obligatorio")
    private String licensePlate;

    @NotNull(message = "La fecha de la cita es obligatoria")
    @Future(message = "La fecha debe ser en futuro")
    private LocalDateTime appointmentDate;

    @NotBlank(message = "Debe seleccionar un tipo de servicio")
    private String serviceType;

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
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

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

}
