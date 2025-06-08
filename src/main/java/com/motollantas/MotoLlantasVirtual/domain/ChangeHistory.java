package com.motollantas.MotoLlantasVirtual.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ChangeHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long employeeId;

    private String action;

    private String description;

    private LocalDateTime date;

    public ChangeHistory() {
    }

    public ChangeHistory(Long employeeId, String action, String description, LocalDateTime date) {
        this.employeeId = employeeId;
        this.action = action;
        this.description = description;
        this.date = date;
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long empleadoId) {
        this.employeeId = empleadoId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String accion) {
        this.action = accion;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
