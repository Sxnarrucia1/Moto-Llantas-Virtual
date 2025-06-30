/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.motollantas.MotoLlantasVirtual.controller;

import com.motollantas.MotoLlantasVirtual.DTO.AdminDateDTO;
import com.motollantas.MotoLlantasVirtual.DTO.AdminMotorcycleDTO;
import com.motollantas.MotoLlantasVirtual.DTO.ClientMotorcycleDTO;
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
import com.motollantas.MotoLlantasVirtual.Service.EmployeeService;
import com.motollantas.MotoLlantasVirtual.Service.MotorcycleService;
import com.motollantas.MotoLlantasVirtual.Service.RepairSubtaskService;
import com.motollantas.MotoLlantasVirtual.Service.UserService;
import com.motollantas.MotoLlantasVirtual.domain.Employee;
import com.motollantas.MotoLlantasVirtual.domain.Motorcycle;
import com.motollantas.MotoLlantasVirtual.domain.User;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Optional;
import org.springframework.web.bind.annotation.ResponseBody;

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
    private EmployeeService employeeService;

    @Autowired
    private UserService userService;

    @Autowired
    private RepairSubtaskService repairSubtaskService;

    @Autowired
    private MotorcycleService motorcycleService;

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

        repairOrderService.createFromAdmin(dto, serviceType);

        redirectAttributes.addFlashAttribute("mensajeExito", "Cita creada exitosamente.");
        return "redirect:/garage/adminGarage";
    }

    @GetMapping("/editAdmin/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        RepairOrder repairOrder = repairOrderService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid order ID: " + id));

        model.addAttribute("subtasks", repairSubtaskService.getSubtasksByOrderId(repairOrder.getId()));
        model.addAttribute("repairOrder", repairOrder);
        model.addAttribute("services", serviceTypeService.findAll());
        model.addAttribute("orderStatuses", OrderStatus.values());
        model.addAttribute("orderPriorities", OrderPriority.values());
        model.addAttribute("mechanics", employeeService.filterByRole("MECANICO"));

        return "garage/fragments :: editAdmin";
    }

    @PostMapping("/updateAdmin")
    public Object updateRepairOrder(@ModelAttribute RepairOrder repairOrder,
            @RequestParam("formattedAppointmentDate") String formattedDate,
            Model model,
            RedirectAttributes redirectAttributes,
            HttpServletRequest request,
            Principal principal) {

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

        User currentUser = userService.findByEmail(principal.getName());
        Optional<Employee> optionalEmployee = employeeService.findByUser(currentUser);

        String redirectUrl;

        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            repairOrderService.updateFromAdminOrMechanic(repairOrder, employee);

            boolean isAdmin = employee.getRoles().stream().anyMatch(r -> r.equalsIgnoreCase("ADMIN"));
            redirectUrl = isAdmin ? "/garage/adminGarage" : "/garage/myOrders";
        } else {
            repairOrderService.updateFromAdmin(repairOrder);
            redirectUrl = "/garage/adminGarage";
        }

        Map<String, String> response = new HashMap<>();
        response.put("redirectUrl", redirectUrl);
        response.put("mensajeExito", "Cita actualizada exitosamente.");
        return ResponseEntity.ok(response);
    }

    private String reloadEditFragment(Model model, RepairOrder repairOrder) {
        RepairOrder fullOrder = repairOrderService.findById(repairOrder.getId())
                .orElse(repairOrder);

        model.addAttribute("repairOrder", fullOrder);
        model.addAttribute("services", serviceTypeService.findAll());
        model.addAttribute("orderStatuses", OrderStatus.values());
        model.addAttribute("orderPriorities", OrderPriority.values());
        model.addAttribute("mechanics", employeeService.filterByRole("MECANICO"));
        return "garage/fragments :: editAdmin";
    }

    @GetMapping("/search")
    @ResponseBody
    public ResponseEntity<?> searchByPlate(@RequestParam String plate, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        Optional<Motorcycle> motorcycleOpt = motorcycleService.findByLicensePlateAndUser(plate, user);

        return motorcycleOpt
                .map(m -> new ClientMotorcycleDTO(
                m.getBrand(),
                m.getModelName(),
                m.getYear(),
                m.getLicensePlate()
        ))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/admin/search")
    @ResponseBody
    public ResponseEntity<?> searchByPlateAdmin(@RequestParam String plate) {
        Optional<Motorcycle> motorcycleOpt = motorcycleService.findByLicensePlate(plate);

        return motorcycleOpt
                .map(m -> new AdminMotorcycleDTO(
                m.getBrand(),
                m.getModelName(),
                m.getYear(),
                m.getLicensePlate()
        ))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/search-history")
    public String searchMotorcycleHistory(@RequestParam(required = false) String licensePlate,
            @RequestParam(required = false) String ownerId,
            Model model) {

        List<RepairOrder> orders = new ArrayList<>();

        if ((licensePlate == null || licensePlate.isBlank()) && (ownerId == null || ownerId.isBlank())) {
            model.addAttribute("mensajeError", "Por favor, ingresa al menos un criterio de búsqueda: número de placa o cédula del dueño.");
            return "garage/motorcycleHistory";
        }

        if (licensePlate != null && !licensePlate.isBlank()) {
            motorcycleService.findByLicensePlate(licensePlate).ifPresentOrElse(moto -> {
                orders.addAll(repairOrderService.findByMotorcycle(moto));
                model.addAttribute("motorcycle", moto);
            }, () -> model.addAttribute("mensajeError", "No se encontró ninguna motocicleta con esa placa."));
        } else if (ownerId != null && !ownerId.isBlank()) {
            List<Motorcycle> motorcycles = motorcycleService.findByOwnerIdentification(ownerId);
            if (motorcycles.isEmpty()) {
                model.addAttribute("mensajeError", "No se encontraron motocicletas asociadas a esa cédula.");
            } else {
                model.addAttribute("motorcycles", motorcycles);
                for (Motorcycle moto : motorcycles) {
                    orders.addAll(repairOrderService.findByMotorcycle(moto));
                }
            }
        }

        if (orders.isEmpty() && !model.containsAttribute("mensajeError")) {
            model.addAttribute("mensajeError", "No se encontraron órdenes de reparación para los criterios ingresados.");
        }

        model.addAttribute("repairOrders", orders);
        model.addAttribute("licensePlate", licensePlate);
        model.addAttribute("ownerId", ownerId);

        return "garage/motorcycleHistory";
    }
}
