/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.motollantas.MotoLlantasVirtual.controller;

import com.motollantas.MotoLlantasVirtual.Service.EmployeeService;
import com.motollantas.MotoLlantasVirtual.Service.RepairOrderService;
import com.motollantas.MotoLlantasVirtual.Service.RepairSubtaskService;
import com.motollantas.MotoLlantasVirtual.Service.ServiceTypeService;
import com.motollantas.MotoLlantasVirtual.domain.OrderPriority;
import com.motollantas.MotoLlantasVirtual.domain.OrderStatus;
import com.motollantas.MotoLlantasVirtual.domain.RepairOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author esteb
 */
@Controller
@RequestMapping("/garage")
public class RepairSubtaskController {

    @Autowired
    private RepairSubtaskService subtaskService;

    @Autowired
    private RepairOrderService repairOrderService;

    @Autowired
    private ServiceTypeService serviceTypeService;

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/addSubtask")
    public String addSubtask(@RequestParam Long repairOrderId,
            @RequestParam String description,
            Model model) {

        RepairOrder order = repairOrderService.findById(repairOrderId)
                .orElseThrow(() -> new IllegalArgumentException("Orden no encontrada"));

        subtaskService.createSubtask(repairOrderId, description);

        model.addAttribute("repairOrder", order);
        model.addAttribute("subtasks", subtaskService.getSubtasksByOrderId(repairOrderId));
        model.addAttribute("services", serviceTypeService.findAll());
        model.addAttribute("orderStatuses", OrderStatus.values());
        model.addAttribute("orderPriorities", OrderPriority.values());
        model.addAttribute("mechanics", employeeService.filterByRole("MECANICO"));

        return "garage/fragments :: editAdmin";
    }

    @PostMapping("/toggleSubtask")
    public String toggleSubtask(@RequestParam Long subtaskId,
            @RequestParam Long repairOrderId,
            Model model) {

        subtaskService.toggleCompletion(subtaskId);

        RepairOrder order = repairOrderService.findById(repairOrderId)
                .orElseThrow(() -> new IllegalArgumentException("Orden no encontrada"));

        model.addAttribute("repairOrder", order);
        model.addAttribute("subtasks", subtaskService.getSubtasksByOrderId(repairOrderId));
        model.addAttribute("services", serviceTypeService.findAll());
        model.addAttribute("orderStatuses", OrderStatus.values());
        model.addAttribute("orderPriorities", OrderPriority.values());
        model.addAttribute("mechanics", employeeService.filterByRole("MECANICO"));

        return "garage/fragments :: editAdmin";
    }

    @PostMapping("/deleteSubtask")
    public String deleteSubtask(@RequestParam Long subtaskId,
            @RequestParam Long repairOrderId,
            Model model) {

        subtaskService.deleteSubtask(subtaskId);

        RepairOrder order = repairOrderService.findById(repairOrderId)
                .orElseThrow(() -> new IllegalArgumentException("Orden no encontrada"));

        model.addAttribute("repairOrder", order);
        model.addAttribute("subtasks", subtaskService.getSubtasksByOrderId(repairOrderId));
        model.addAttribute("services", serviceTypeService.findAll());
        model.addAttribute("orderStatuses", OrderStatus.values());
        model.addAttribute("orderPriorities", OrderPriority.values());
        model.addAttribute("mechanics", employeeService.filterByRole("MECANICO"));

        return "garage/fragments :: editAdmin";
    }
}
