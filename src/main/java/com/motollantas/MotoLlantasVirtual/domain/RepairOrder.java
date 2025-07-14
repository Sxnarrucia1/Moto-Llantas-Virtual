/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.motollantas.MotoLlantasVirtual.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author esteb
 */
@Entity
@Table(name = "repair_orders")
public class RepairOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_usuario")
    private User user;

    private String fullName;
    private String identification;

    private LocalDateTime appointmentDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus = OrderStatus.NUEVO;

    @ManyToOne
    private ServiceType serviceType;

    @ManyToOne
    private Employee mechanic;

    @Enumerated(EnumType.STRING)
    private OrderPriority priority = OrderPriority.BAJA;

    private String problemDescription;

    @Transient
    @SuppressWarnings("unused")
    private String formattedAppointmentDate;

    @OneToMany(mappedBy = "repairOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RepairOrderFile> files = new ArrayList<>();

    @OneToMany(mappedBy = "repairOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RepairSubtask> subtasks = new ArrayList<>();

    @OneToMany(mappedBy = "repairOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RepairOrderProduct> usedProducts = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "motorcycle_id")
    private Motorcycle motorcycle;

    public String getFormattedAppointmentDate() {
        if (appointmentDate != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            return appointmentDate.format(formatter);
        }
        return null;
    }

    public void setFormattedAppointmentDate(String formattedAppointmentDate) {
        this.formattedAppointmentDate = formattedAppointmentDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identificacion) {
        this.identification = identificacion;
    }

    public LocalDateTime getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDateTime appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    public Employee getMechanic() {
        return mechanic;
    }

    public void setMechanic(Employee mechanic) {
        this.mechanic = mechanic;
    }

    public OrderPriority getPriority() {
        return priority;
    }

    public void setPriority(OrderPriority priority) {
        this.priority = priority;
    }

    public String getProblemDescription() {
        return problemDescription;
    }

    public void setProblemDescription(String problemDescription) {
        this.problemDescription = problemDescription;
    }

    public List<RepairOrderFile> getFiles() {
        return files;
    }

    public void setFiles(List<RepairOrderFile> files) {
        this.files = files;
    }

    public List<RepairSubtask> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(List<RepairSubtask> subtasks) {
        this.subtasks = subtasks;
    }

    public Motorcycle getMotorcycle() {
        return motorcycle;
    }

    public void setMotorcycle(Motorcycle motorcycle) {
        this.motorcycle = motorcycle;
    }

    public List<RepairOrderProduct> getUsedProducts() {
        return usedProducts;
    }

    public void setUsedProducts(List<RepairOrderProduct> usedProducts) {
        this.usedProducts = usedProducts;
    }

}
