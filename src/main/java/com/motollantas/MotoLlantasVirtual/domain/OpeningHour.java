/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.motollantas.MotoLlantasVirtual.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.DayOfWeek;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author esteb
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class OpeningHour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSchedule;

    private DayOfWeek day;
    private LocalTime startTime;
    private LocalTime endTime;

    public LocalTime getStart() {
        return startTime;
    }

    public LocalTime getClose() {
        return endTime;
    }

    public OpeningHour(DayOfWeek day, LocalTime startTime, LocalTime endTime) {
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
