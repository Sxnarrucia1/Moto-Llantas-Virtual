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
import com.motollantas.MotoLlantasVirtual.dao.ServiceTypeDao;
import java.time.Duration;

/**
 *
 * @author esteb
 */
@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initHorarios(OpeningHoursDao dao) {
        return args -> {
            if (dao.count() == 0) {
                dao.save(new OpeningHour(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(16, 30)));
                dao.save(new OpeningHour(DayOfWeek.TUESDAY, LocalTime.of(9, 0), LocalTime.of(16, 30)));
                dao.save(new OpeningHour(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(16, 30)));
                dao.save(new OpeningHour(DayOfWeek.THURSDAY, LocalTime.of(9, 0), LocalTime.of(16, 30)));
                dao.save(new OpeningHour(DayOfWeek.FRIDAY, LocalTime.of(9, 0), LocalTime.of(16, 30)));
                dao.save(new OpeningHour(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(13, 30)));
            }
        };
    }

    @Bean
    public CommandLineRunner initServiceTypes(ServiceTypeDao serviceTypeRepository) {
        return args -> {
            if (serviceTypeRepository.count() == 0) {
                serviceTypeRepository.save(new ServiceType("Cambio de aceite", Duration.ofMinutes(45)));
                serviceTypeRepository.save(new ServiceType("Cambio Llantas", Duration.ofMinutes(45)));
                serviceTypeRepository.save(new ServiceType("Balanceo", Duration.ofMinutes(15)));
                serviceTypeRepository.save(new ServiceType("Revisión general", Duration.ofMinutes(30)));
            }
        };
    }
}
