/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.motollantas.MotoLlantasVirtual.controller;

import com.motollantas.MotoLlantasVirtual.Service.EmployeeService;
import com.motollantas.MotoLlantasVirtual.Service.RepairOrderService;
import com.motollantas.MotoLlantasVirtual.Service.UserService;
import com.motollantas.MotoLlantasVirtual.domain.Employee;
import com.motollantas.MotoLlantasVirtual.domain.OrderStatus;
import com.motollantas.MotoLlantasVirtual.domain.RepairOrder;
import com.motollantas.MotoLlantasVirtual.domain.User;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author esteb
 */
@Controller
@RequestMapping("/garage")
public class MechanicController {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    UserService userService;

    @Autowired
    RepairOrderService repairOrderService;

    @GetMapping("/myOrders")
    public String mostrarOrdenesMecanico(Model model, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        Optional<Employee> optionalMechanic = employeeService.findByUser(user);

        if (optionalMechanic.isEmpty()) {
            model.addAttribute("mensajeError", "No se encontró un perfil de mecánico asociado a este usuario.");
            return "/";
        }

        Employee mechanic = optionalMechanic.get();

        List<RepairOrder> nuevas = repairOrderService.findByMechanicAndOrderStatusOrderByAppointmentDateAsc(mechanic, OrderStatus.NUEVO);
        List<RepairOrder> enProgreso = repairOrderService.findByMechanicAndOrderStatusOrderByAppointmentDateAsc(mechanic, OrderStatus.EN_PROGRESO);
        List<RepairOrder> completadas = repairOrderService.findByMechanicAndOrderStatusOrderByAppointmentDateAsc(mechanic, OrderStatus.COMPLETADO);
        List<RepairOrder> canceladas = repairOrderService.findByMechanicAndOrderStatusOrderByAppointmentDateAsc(mechanic, OrderStatus.CANCELADO);

        model.addAttribute("nuevas", nuevas);
        model.addAttribute("enProgreso", enProgreso);
        model.addAttribute("completadas", completadas);
        model.addAttribute("canceladas", canceladas);
        return "garage/myOrders";
    }

}
