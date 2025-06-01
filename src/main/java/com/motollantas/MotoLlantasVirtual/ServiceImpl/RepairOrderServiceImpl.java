/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.motollantas.MotoLlantasVirtual.ServiceImpl;

import com.motollantas.MotoLlantasVirtual.DTO.ClientDateDTO;
import com.motollantas.MotoLlantasVirtual.Service.RepairOrderService;
import com.motollantas.MotoLlantasVirtual.dao.RepairOrderDao;
import com.motollantas.MotoLlantasVirtual.dao.UserDao;
import com.motollantas.MotoLlantasVirtual.domain.RepairOrder;
import com.motollantas.MotoLlantasVirtual.domain.User;
import java.time.Duration;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 *
 * @author esteb
 */
@Service
public class RepairOrderServiceImpl implements RepairOrderService {

    @Autowired
    RepairOrderDao repair;

    @Autowired
    UserDao user;

    @Override
    public void createDateClient(ClientDateDTO clientDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // Este es el email del usuario logueado

        User client = user.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario No encontrado"));

        RepairOrder orden = new RepairOrder();
        orden.setUser(client);
        orden.setClientName(client.getFullName());
        orden.setIdentificacion(client.getIdentification());
        orden.setModelName(clientDto.getModelName());
        orden.setLicensePlate(clientDto.getLicensePlate());
        orden.setAppointmentDate(clientDto.getAppointmentDate());
        orden.setServiceType(clientDto.getServiceType());
        orden.setBrand(clientDto.getBrand());
        orden.setYear(clientDto.getYear());

        repair.save(orden);
    }

    @Override
    public boolean existsByAppointmentDate(LocalDateTime dateTime) {
        return repair.existsByAppointmentDate(dateTime);
    }

    @Override
    public boolean hasOverlappingAppointment(LocalDateTime start, Duration duration) {
        LocalDateTime end = start.plus(duration);
        return repair.hasOverlappingAppointments(start, end);
    }

}
