/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.motollantas.MotoLlantasVirtual.Service;

import com.motollantas.MotoLlantasVirtual.domain.HorarioAtencion;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author esteb
 */
public interface HorarioAtencionService {
    
    public Optional<HorarioAtencion>findByDia (DayOfWeek dia);
    
    public void Save (HorarioAtencion horario);
    
    public List<HorarioAtencion> getHorarios();
    
}
