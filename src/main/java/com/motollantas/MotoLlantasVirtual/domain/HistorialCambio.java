package com.motollantas.MotoLlantasVirtual.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class HistorialCambio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long empleadoId;

    private String accion; // ejemplo: "Asignación de rol", "Modificación de datos"

    private String descripcion; // detalle del cambio realizado

    private LocalDateTime fecha;

    public HistorialCambio() {
    }

    public HistorialCambio(Long empleadoId, String accion, String descripcion, LocalDateTime fecha) {
        this.empleadoId = empleadoId;
        this.accion = accion;
        this.descripcion = descripcion;
        this.fecha = fecha;
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public Long getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(Long empleadoId) {
        this.empleadoId = empleadoId;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}
