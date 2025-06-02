/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.motollantas.MotoLlantasVirtual.dao;

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

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM RepairOrder r "
            + "WHERE (r.appointmentDate < :end AND :start < r.appointmentDate)")
    boolean hasOverlappingAppointments(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
    
    List <RepairOrder> findByUserId (Long userId);

}
