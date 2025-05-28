/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.motollantas.MotoLlantasVirtual.ServiceImpl;

import com.motollantas.MotoLlantasVirtual.Service.HorarioAtencionService;
import com.motollantas.MotoLlantasVirtual.Dao.HorarioAtencionDao;
import com.motollantas.MotoLlantasVirtual.Domain.HorarioAtencion;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author esteb
 */
public class HorarioAtencionServiceImpl implements HorarioAtencionService {

    @Autowired
    private HorarioAtencionDao horarioAtencion;

    @Override
    public Optional <HorarioAtencion> findByDia(DayOfWeek dia) {
        return horarioAtencion.findByDia(dia);
    }

    @Override
    public void Save(HorarioAtencion horario) {
        horarioAtencion.save(horario);
    }

    @Override
    public List<HorarioAtencion> getHorarios() {
        return horarioAtencion.findAll();
    }

}
