/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.motollantas.MotoLlantasVirtual.controller;

import com.motollantas.MotoLlantasVirtual.DTO.AdminDateDTO;
import com.motollantas.MotoLlantasVirtual.Service.EmpleadoService;
import com.motollantas.MotoLlantasVirtual.Service.RepairOrderService;
import com.motollantas.MotoLlantasVirtual.Service.ServiceTypeService;
import com.motollantas.MotoLlantasVirtual.ServiceImpl.DateValidator;
import com.motollantas.MotoLlantasVirtual.domain.OrderPriority;
import com.motollantas.MotoLlantasVirtual.domain.OrderStatus;
import com.motollantas.MotoLlantasVirtual.domain.RepairOrder;
import com.motollantas.MotoLlantasVirtual.domain.ServiceType;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author esteb
 */
@Controller
@RequestMapping("/garage")
public class RepairOrderController {

    @Autowired
    private RepairOrderService repairOrderService;

    @Autowired
    private ServiceTypeService serviceTypeService;

    @Autowired
    private DateValidator dateValidator;

    @Autowired
    private EmpleadoService employeeService;

    @GetMapping("/adminGarage")
    public String mostrarOrdenes(Model model) {
        List<RepairOrder> nuevas = repairOrderService.findByStatusASC(OrderStatus.NUEVO);
        List<RepairOrder> enProgreso = repairOrderService.findByStatusASC(OrderStatus.EN_PROGRESO);
        List<RepairOrder> completadas = repairOrderService.findByStatusASC(OrderStatus.COMPLETADO);
        List<RepairOrder> canceladas = repairOrderService.findByStatusASC(OrderStatus.CANCELADO);

        model.addAttribute("nuevas", nuevas);
        model.addAttribute("enProgreso", enProgreso);
        model.addAttribute("completadas", completadas);
        model.addAttribute("canceladas", canceladas);
        model.addAttribute("services", serviceTypeService.findAll());
        return "garage/adminGarage";
    }

    @PostMapping("/create")
    public String createAppointment(@ModelAttribute AdminDateDTO dto, RedirectAttributes redirectAttributes) {
        // Validaciones
        if (dateValidator.isClosedDay(dto.getAppointmentDate())) {
            redirectAttributes.addFlashAttribute("mensajeError", "El taller está cerrado ese día.");
            return "redirect:/garage/adminGarage";
        }

        if (!dateValidator.isWithinOpeningHours(dto.getAppointmentDate())) {
            redirectAttributes.addFlashAttribute("mensajeError", "La hora seleccionada está fuera del horario de atención.");
            return "redirect:/garage/adminGarage";
        }

        ServiceType serviceType = serviceTypeService.findById(dto.getServiceTypeId());
        if (!dateValidator.isSlotAvailable(dto.getAppointmentDate(), serviceType)) {
            redirectAttributes.addFlashAttribute("mensajeError", "Ya hay una cita en ese horario.");
            return "redirect:/garage/adminGarage";
        }

        // Crear orden
        repairOrderService.createFromAdmin(dto, serviceType);

        redirectAttributes.addFlashAttribute("mensajeExito", "Cita creada exitosamente.");
        return "redirect:/garage/adminGarage";
    }

    @GetMapping("/editAdmin/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        RepairOrder repairOrder = repairOrderService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid order ID: " + id));

        model.addAttribute("repairOrder", repairOrder);
        model.addAttribute("services", serviceTypeService.findAll());
        model.addAttribute("orderStatuses", OrderStatus.values());
        model.addAttribute("orderPriorities", OrderPriority.values());
        model.addAttribute("mechanics", employeeService.filtrarPorRol("MECANICO"));

        return "garage/fragments :: editAdmin";
    }

    @PostMapping("/updateAdmin")
    public Object updateRepairOrder(@ModelAttribute RepairOrder repairOrder,
            @RequestParam("formattedAppointmentDate") String formattedDate,
            Model model,
            RedirectAttributes redirectAttributes,
            HttpServletRequest request) {

        boolean isAjax = "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            LocalDateTime parsedDate = LocalDateTime.parse(formattedDate, formatter);
            repairOrder.setAppointmentDate(parsedDate);
        } catch (DateTimeParseException e) {
            model.addAttribute("mensajeError", "Formato de fecha inválido.");
            return isAjax ? reloadEditFragment(model, repairOrder) : "garage/fragments :: editAdmin";
        }

        LocalDateTime appointmentDate = repairOrder.getAppointmentDate();

        if (appointmentDate.isBefore(LocalDateTime.now())) {
            model.addAttribute("mensajeError", "No se puede agendar una cita en el pasado.");
            return isAjax ? reloadEditFragment(model, repairOrder) : "garage/fragments :: editAdmin";
        }

        if (dateValidator.isClosedDay(appointmentDate)) {
            model.addAttribute("mensajeError", "El taller está cerrado ese día.");
            return isAjax ? reloadEditFragment(model, repairOrder) : "garage/fragments :: editAdmin";
        }

        if (!dateValidator.isWithinOpeningHours(appointmentDate)) {
            model.addAttribute("mensajeError", "La hora seleccionada está fuera del horario de atención.");
            return isAjax ? reloadEditFragment(model, repairOrder) : "garage/fragments :: editAdmin";
        }

        repairOrderService.updateFromAdmin(repairOrder);
        Map<String, String> response = new HashMap<>();
        response.put("redirectUrl", "/garage/adminGarage");
        response.put("mensajeExito", "Cita actualizada exitosamente.");
        return ResponseEntity.ok(response);
    }

    private String reloadEditFragment(Model model, RepairOrder repairOrder) {
        RepairOrder fullOrder = repairOrderService.findById(repairOrder.getIdOrden())
                .orElse(repairOrder);

        model.addAttribute("repairOrder", fullOrder);
        model.addAttribute("services", serviceTypeService.findAll());
        model.addAttribute("orderStatuses", OrderStatus.values());
        model.addAttribute("orderPriorities", OrderPriority.values());
        model.addAttribute("mechanics", employeeService.filtrarPorRol("MECANICO"));
        return "garage/fragments :: editAdmin";
    }

}
