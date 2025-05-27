/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.motollantas.MotoLlantasVirtual.domain;

import com.motollantas.MotoLlantasVirtual.dao.HorarioAtencionDao;
import java.time.DayOfWeek;
import java.time.LocalTime;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author esteb
 */
@Configuration
public class DataInitializer {
    
    @Bean
    public CommandLineRunner initHorarios(HorarioAtencionDao dao){
        return args -> {
        if (dao.count() == 0){
            dao.save(new HorarioAtencion(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(17, 0)));
            dao.save(new HorarioAtencion(DayOfWeek.TUESDAY, LocalTime.of(9, 0), LocalTime.of(17, 0)));
            dao.save(new HorarioAtencion(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(17, 0)));
            dao.save(new HorarioAtencion(DayOfWeek.THURSDAY, LocalTime.of(9, 0), LocalTime.of(17, 0)));
            dao.save(new HorarioAtencion(DayOfWeek.FRIDAY, LocalTime.of(9, 0), LocalTime.of(17, 0)));
            dao.save(new HorarioAtencion(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(14, 0)));
        }
        };
    }
}
