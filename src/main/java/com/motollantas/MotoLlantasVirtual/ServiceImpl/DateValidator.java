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

    private final Map<DayOfWeek, LocalTime[]> horarioDefault = Map.of(
            DayOfWeek.MONDAY, new LocalTime[]{LocalTime.of(9, 0), LocalTime.of(17, 0)},
            DayOfWeek.TUESDAY, new LocalTime[]{LocalTime.of(9, 0), LocalTime.of(17, 0)},
            DayOfWeek.WEDNESDAY, new LocalTime[]{LocalTime.of(9, 0), LocalTime.of(17, 0)},
            DayOfWeek.THURSDAY, new LocalTime[]{LocalTime.of(9, 0), LocalTime.of(17, 0)},
            DayOfWeek.FRIDAY, new LocalTime[]{LocalTime.of(9, 0), LocalTime.of(17, 0)},
            DayOfWeek.SATURDAY, new LocalTime[]{LocalTime.of(9, 0), LocalTime.of(17, 0)}
    );

    public boolean isValidDate(LocalDateTime appointmentDate) {
        DayOfWeek day = appointmentDate.getDayOfWeek();
        LocalTime hour = appointmentDate.toLocalTime();

        Optional<OpeningHour> scheduleOpt = openingService.findByDay(day);

        LocalTime startTime = scheduleOpt.map(OpeningHour::getStart)
                .orElse(horarioDefault.getOrDefault(day, null)
                        != null ? horarioDefault.get(day)[0] : null);

        LocalTime endTime = scheduleOpt.map(OpeningHour::getClose)
                .orElse(horarioDefault.getOrDefault(day, null)
                        != null ? horarioDefault.get(day)[1] : null);
        
        if (startTime == null || endTime == null) {
            return false;
        }
        
        return !hour.isBefore(startTime) && !hour.isAfter(endTime);
    }
}
