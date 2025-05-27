/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.motollantas.MotoLlantasVirtual.domain;

import jakarta.persistence.Entity;
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
public class Repair_Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_orden;
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private User usuario;
    private String nombre_cliente;
    private String modelo_moto;
    private int anio;
    private int cilindraje;
    private int kilometraje;
    private String numero_placa;
    private String color;
    @NotNull
    @Future
    private LocalDateTime fecha_cita;
    private OrderStatus estadoCita = OrderStatus.NUEVO;
}
