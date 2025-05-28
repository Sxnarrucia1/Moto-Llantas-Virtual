/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.motollantas.MotoLlantasVirtual.Dao;

import com.motollantas.MotoLlantasVirtual.Domain.HorarioAtencion;
import java.time.DayOfWeek;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author esteb
 */
public interface HorarioAtencionDao extends JpaRepository<HorarioAtencion, Long> {

    public Optional<HorarioAtencion> findByDia(DayOfWeek dia);
    
    
}
