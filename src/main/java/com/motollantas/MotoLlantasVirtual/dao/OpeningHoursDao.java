/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.motollantas.MotoLlantasVirtual.dao;

import com.motollantas.MotoLlantasVirtual.domain.OpeningHour;
import java.time.DayOfWeek;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author esteb
 */
public interface OpeningHoursDao extends JpaRepository<OpeningHour, Long> {

    public Optional<OpeningHour> findByDay(DayOfWeek day);
    
    
}
