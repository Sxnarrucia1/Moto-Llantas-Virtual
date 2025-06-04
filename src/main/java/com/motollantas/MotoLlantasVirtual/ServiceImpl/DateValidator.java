/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.motollantas.MotoLlantasVirtual.ServiceImpl;

import com.motollantas.MotoLlantasVirtual.domain.OpeningHour;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import com.motollantas.MotoLlantasVirtual.Service.OpeningHourService;
import com.motollantas.MotoLlantasVirtual.Service.RepairOrderService;
import com.motollantas.MotoLlantasVirtual.domain.ServiceType;
import org.springframework.stereotype.Component;

/**
 *
 * @author esteb
 */
@Component
public class DateValidator {

    @Autowired
    private OpeningHourService openingService;

    @Autowired
    private RepairOrderService repairService;

    public boolean isClosedDay(LocalDateTime appointmentDate) {
        DayOfWeek day = appointmentDate.getDayOfWeek();
        Optional<OpeningHour> scheduleOpt = openingService.findByDay(day);
        return scheduleOpt.isEmpty();
    }

    public boolean isWithinOpeningHours(LocalDateTime appointmentDate) {
        DayOfWeek day = appointmentDate.getDayOfWeek();
        LocalTime hour = appointmentDate.toLocalTime();

        Optional<OpeningHour> scheduleOpt = openingService.findByDay(day);
        if (scheduleOpt.isEmpty()) {
            return false;
        }

        LocalTime startTime = scheduleOpt.get().getStart();
        LocalTime endTime = scheduleOpt.get().getClose();
        return startTime != null && endTime != null
                && !hour.isBefore(startTime) && !hour.isAfter(endTime);
    }

    public boolean isSlotAvailable(LocalDateTime appointmentDate, ServiceType serviceType) {
        return !repairService.hasOverlappingAppointment(appointmentDate, serviceType);
    }
}
