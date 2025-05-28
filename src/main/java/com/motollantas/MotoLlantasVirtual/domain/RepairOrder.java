/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.motollantas.MotoLlantasVirtual.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Future;
import lombok.Data;

/**
 *
 * @author esteb
 */
@Entity
@Data
@Table(name = "repair_orders")
public class RepairOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_orden;
    
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private User user;
    
    private String clientName;
    private String modelName;
    private int year;
    private int displacement;
    private int kilometraje;
    private String licensePlate;
    private String color;
    
    @NotNull
    @Future
    private LocalDateTime appointmentDate;
    
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus = OrderStatus.NUEVO;
    
    private String serviceType;
    
    @ManyToOne
    private Employee mechanic;
    
    @Enumerated(EnumType.STRING)
    private OrderPriority priority = OrderPriority.BAJA;
    
    private String problemDescription;
}
