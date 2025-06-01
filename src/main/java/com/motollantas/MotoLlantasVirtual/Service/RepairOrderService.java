/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.motollantas.MotoLlantasVirtual.Service;

import com.motollantas.MotoLlantasVirtual.DTO.ClientDateDTO;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 *
 * @author esteb
 */
public interface RepairOrderService {
    
    public void createDateClient (ClientDateDTO clientDto);
    
    public boolean existsByAppointmentDate (LocalDateTime dateTime);
    
    public boolean hasOverlappingAppointment (LocalDateTime start, Duration duration);
}
