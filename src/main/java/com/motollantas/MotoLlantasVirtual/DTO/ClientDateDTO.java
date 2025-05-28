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
public class ClientDateDTO {
    
    @NotBlank (message = "El nombre del cliente es obligatorio")
    private String clientName;
    
    @NotBlank (message = "El modelo de la moto es obligatorio")
    private String modelName;
    
    @Min (value = 1900, message = "El año debe ser valido")
    private int year;
    private int displacement;
    private int kilometraje;
    
    @NotBlank (message = "El número de placa de la moto es obligatorio")
    private String licensePlate;
    
    @NotNull (message = "La fecha de la cita es obligatoria")
    @Future (message = "La fecha debe ser en futuro")
    private LocalDateTime appointmentDate;
    
    @NotBlank (message = "Debe seleccionar un tipo de servicio")
    private String serviceType;
}
