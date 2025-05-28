/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.motollantas.MotoLlantasVirtual.ServiceImpl;

import com.motollantas.MotoLlantasVirtual.domain.OpeningHour;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import com.motollantas.MotoLlantasVirtual.dao.OpeningHoursDao;
import com.motollantas.MotoLlantasVirtual.Service.OpeningHourService;

/**
 *
 * @author esteb
 */
public class OpeningHourServiceImpl implements OpeningHourService {

    @Autowired
    private OpeningHoursDao openingHours;

    @Override
    public Optional <OpeningHour> findByDay(DayOfWeek dia) {
        return openingHours.findByDay(dia);
    }

    @Override
    public void Save(OpeningHour schedule) {
        openingHours.save(schedule);
    }

    @Override
    public List<OpeningHour> getSchedule() {
        return openingHours.findAll();
    }

}
