/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.motollantas.MotoLlantasVirtual.Service;

import com.motollantas.MotoLlantasVirtual.domain.OpeningHour;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author esteb
 */
public interface OpeningHourService {
    
    public Optional<OpeningHour>findByDay (DayOfWeek day);
    
    public void Save (OpeningHour schedule);
    
    public List<OpeningHour> getSchedule();
    
}
