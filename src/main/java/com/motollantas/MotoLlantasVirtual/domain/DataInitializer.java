/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.motollantas.MotoLlantasVirtual.domain;

import java.time.DayOfWeek;
import java.time.LocalTime;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.motollantas.MotoLlantasVirtual.dao.OpeningHoursDao;

/**
 *
 * @author esteb
 */
@Configuration
public class DataInitializer {
    
    @Bean
    public CommandLineRunner initHorarios(OpeningHoursDao dao){
        return args -> {
        if (dao.count() == 0){
            dao.save(new OpeningHour(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(17, 0)));
            dao.save(new OpeningHour(DayOfWeek.TUESDAY, LocalTime.of(9, 0), LocalTime.of(17, 0)));
            dao.save(new OpeningHour(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(17, 0)));
            dao.save(new OpeningHour(DayOfWeek.THURSDAY, LocalTime.of(9, 0), LocalTime.of(17, 0)));
            dao.save(new OpeningHour(DayOfWeek.FRIDAY, LocalTime.of(9, 0), LocalTime.of(17, 0)));
            dao.save(new OpeningHour(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(14, 0)));
        }
        };
    }
}
