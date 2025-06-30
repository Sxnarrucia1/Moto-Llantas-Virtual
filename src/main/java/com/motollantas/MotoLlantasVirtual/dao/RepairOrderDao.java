/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.motollantas.MotoLlantasVirtual.dao;

import com.motollantas.MotoLlantasVirtual.domain.Employee;
import com.motollantas.MotoLlantasVirtual.domain.Motorcycle;
import com.motollantas.MotoLlantasVirtual.domain.OrderStatus;
import com.motollantas.MotoLlantasVirtual.domain.RepairOrder;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author esteb
 */
public interface RepairOrderDao extends JpaRepository<RepairOrder, Long> {

    boolean existsByAppointmentDate(LocalDateTime appointmentDate);

    @Query("SELECT r FROM RepairOrder r WHERE r.appointmentDate BETWEEN :startOfDay AND :endOfDay")
    List<RepairOrder> findAppointmentsInDay(@Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay);

    List<RepairOrder> findByUserId(Long userId);

    List<RepairOrder> findByOrderStatus(OrderStatus status);

    List<RepairOrder> findByOrderStatusOrderByAppointmentDateAsc(OrderStatus status);
    
    List<RepairOrder> findByMechanicAndOrderStatusOrderByAppointmentDateAsc(Employee mechanic, OrderStatus status);
    
    List<RepairOrder> findByMotorcycle(Motorcycle motorcycle);

}
