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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
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

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void createDateClient(ClientDateDTO clientDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

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

    @Override
    public List<ClientDateDTO> getAppointmentbyUser(Long userId) {
        List<RepairOrder> orders = repair.findByUserId(userId);
        return orders.stream()
                .map(orden -> modelMapper.map(orden, ClientDateDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<RepairOrder> findById(Long id) {
        return repair.findById(id);
    }

    @Override
    public void updateDateClient(ClientDateDTO dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User client = user.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario No encontrado"));
        RepairOrder orden = repair.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));

        orden.setUser(client);
        orden.setClientName(client.getFullName());
        orden.setIdentificacion(client.getIdentification());
        orden.setAppointmentDate(dto.getAppointmentDate());
        orden.setServiceType(dto.getServiceType());
        orden.setBrand(dto.getBrand());
        orden.setModelName(dto.getModelName());
        orden.setYear(dto.getYear());
        orden.setLicensePlate(dto.getLicensePlate());

        repair.save(orden);
    }

    @Override
    public void deleteById(Long id) {
        repair.deleteById(id);
    }

}
