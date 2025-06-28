/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.motollantas.MotoLlantasVirtual.Service;

import com.motollantas.MotoLlantasVirtual.DTO.AdminDateDTO;
import com.motollantas.MotoLlantasVirtual.DTO.ClientDateDTO;
import com.motollantas.MotoLlantasVirtual.domain.Employee;
import com.motollantas.MotoLlantasVirtual.domain.OrderStatus;
import com.motollantas.MotoLlantasVirtual.domain.RepairOrder;
import com.motollantas.MotoLlantasVirtual.domain.ServiceType;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author esteb
 */
public interface RepairOrderService {

    public void createDateClient(ClientDateDTO clientDto);

    public boolean existsByAppointmentDate(LocalDateTime dateTime);

    public boolean hasOverlappingAppointment(LocalDateTime newStart, ServiceType serviceType);

    public List<ClientDateDTO> getAppointmentbyUser(Long userId);

    public Optional<RepairOrder> findById(Long id);

    public void updateDateClient(ClientDateDTO dto);

    public void deleteById(Long id);

    public List<RepairOrder> findByStatus(OrderStatus status);

    public List<RepairOrder> findByStatusASC(OrderStatus status);

    public void createFromAdmin(AdminDateDTO dto, ServiceType serviceType);

    public void updateFromAdmin(RepairOrder updatedOrder);

    public List<RepairOrder> findByMechanicAndOrderStatusOrderByAppointmentDateAsc(Employee mechanic, OrderStatus status);

    public void updateFromAdminOrMechanic(RepairOrder updatedOrder, Employee currentEmployee);
    
    public List<RepairOrder> findAll();
    
    
}
