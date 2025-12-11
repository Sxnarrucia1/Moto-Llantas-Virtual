/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.motollantas.MotoLlantasVirtual.domain;

/**
 *
 * @author esteb
 */

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "motorcycles")
public class Motorcycle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    private String brand;

    private String modelName;

    @Min(value = 1900, message = "El año debe ser mayor a 1900")
    private int year;

    @Min(value = 0, message = "El cilindraje no puede ser negativo")
    private Integer displacement;

    @Min(value = 0, message = "El kilometraje no puede ser negativo")
    private Integer kilometraje;

    private String licensePlate;

    private String color;

    @OneToMany(mappedBy = "motorcycle", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RepairOrder> repairOrders = new ArrayList<>();

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

    public Integer getDisplacement() {
        return displacement;
    }

    public void setDisplacement(Integer displacement) {
        this.displacement = displacement;
    }

    public Integer getKilometraje() {
        return kilometraje;
    }

    public void setKilometraje(Integer kilometraje) {
        this.kilometraje = kilometraje;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<RepairOrder> getRepairOrders() {
        return repairOrders;
    }

    public void setRepairOrders(List<RepairOrder> repairOrders) {
        this.repairOrders = repairOrders;
    }

    public Motorcycle() {
    }

    public Motorcycle(User owner, String brand, String modelName, int year, Integer displacement, Integer kilometraje, String licensePlate, String color) {
        this.user = owner;
        this.brand = brand;
        this.modelName = modelName;
        this.year = year;
        this.displacement = displacement;
        this.kilometraje = kilometraje;
        this.licensePlate = licensePlate;
        this.color = color;
    }

}
