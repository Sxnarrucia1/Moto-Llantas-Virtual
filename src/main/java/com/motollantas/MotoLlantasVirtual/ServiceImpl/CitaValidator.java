/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.motollantas.MotoLlantasVirtual.ServiceImpl;

import com.motollantas.MotoLlantasVirtual.Service.HorarioAtencionService;
import com.motollantas.MotoLlantasVirtual.domain.HorarioAtencion;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author esteb
 */
public class CitaValidator {

    @Autowired
    private HorarioAtencionService horarioService;

    private final Map<DayOfWeek, LocalTime[]> horarioDefault = Map.of(
            DayOfWeek.MONDAY, new LocalTime[]{LocalTime.of(9, 0), LocalTime.of(17, 0)},
            DayOfWeek.TUESDAY, new LocalTime[]{LocalTime.of(9, 0), LocalTime.of(17, 0)},
            DayOfWeek.WEDNESDAY, new LocalTime[]{LocalTime.of(9, 0), LocalTime.of(17, 0)},
            DayOfWeek.THURSDAY, new LocalTime[]{LocalTime.of(9, 0), LocalTime.of(17, 0)},
            DayOfWeek.FRIDAY, new LocalTime[]{LocalTime.of(9, 0), LocalTime.of(17, 0)},
            DayOfWeek.SATURDAY, new LocalTime[]{LocalTime.of(9, 0), LocalTime.of(17, 0)}
    );

    public boolean esCitaValida(LocalDateTime fechaHoraCita) {
        DayOfWeek dia = fechaHoraCita.getDayOfWeek();
        LocalTime hora = fechaHoraCita.toLocalTime();

        Optional<HorarioAtencion> horarioOpt = horarioService.findByDia(dia);

        LocalTime horaInicio = horarioOpt.map(HorarioAtencion::getHoraInicio)
                .orElse(horarioDefault.getOrDefault(dia, null)
                        != null ? horarioDefault.get(dia)[0] : null);

        LocalTime horaFin = horarioOpt.map(HorarioAtencion::getHoraFin)
                .orElse(horarioDefault.getOrDefault(dia, null)
                        != null ? horarioDefault.get(dia)[1] : null);
        
        if (horaInicio == null || horaFin == null) {
            return false;
        }
        
        return !hora.isBefore(horaInicio) && !hora.isAfter(horaFin);
    }
}
