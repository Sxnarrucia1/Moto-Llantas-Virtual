/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.motollantas.MotoLlantasVirtual.controller;

import com.motollantas.MotoLlantasVirtual.Service.RepairOrderService;
import com.motollantas.MotoLlantasVirtual.domain.OrderStatus;
import com.motollantas.MotoLlantasVirtual.domain.RepairOrder;
import java.util.List;
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
public class RepairOrderController {

    @Autowired
    private RepairOrderService repairOrderService;

    @GetMapping("/adminGarage")
    public String mostrarOrdenes(Model model) {
        List<RepairOrder> nuevas = repairOrderService.findByStatus(OrderStatus.NUEVO);
        List<RepairOrder> enProgreso = repairOrderService.findByStatus(OrderStatus.EN_PROGRESO);
        List<RepairOrder> completadas = repairOrderService.findByStatus(OrderStatus.COMPLETADO);
        List<RepairOrder> canceladas = repairOrderService.findByStatus(OrderStatus.CANCELADO);
        System.out.println(nuevas);

        model.addAttribute("nuevas", nuevas);
        model.addAttribute("enProgreso", enProgreso);
        model.addAttribute("completadas", completadas);
        model.addAttribute("canceladas", canceladas);
        return "garage/adminGarage";
    }
}
