/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.motollantas.MotoLlantasVirtual.ServiceImpl;

import com.motollantas.MotoLlantasVirtual.domain.OpeningHour;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import com.motollantas.MotoLlantasVirtual.Service.OpeningHourService;

/**
 *
 * @author esteb
 */
public class DateValidator {

    @Autowired
    private OpeningHourService openingService;

    public boolean isValidDate(LocalDateTime appointmentDate) {
        DayOfWeek day = appointmentDate.getDayOfWeek();
        LocalTime hour = appointmentDate.toLocalTime();

        Optional<OpeningHour> scheduleOpt = openingService.findByDay(day);
        
        System.out.println(scheduleOpt);

        LocalTime startTime = scheduleOpt.get().getStart();

        LocalTime endTime = scheduleOpt.get().getClose();
        
        if (startTime == null || endTime == null) {
            return false;
        }
        
        return !hour.isBefore(startTime) && !hour.isAfter(endTime);
    }
}
