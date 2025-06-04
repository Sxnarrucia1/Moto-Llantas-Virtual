/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.motollantas.MotoLlantasVirtual.controller;

import com.motollantas.MotoLlantasVirtual.DTO.ClientDateDTO;
import com.motollantas.MotoLlantasVirtual.Service.RepairOrderService;
import com.motollantas.MotoLlantasVirtual.Service.ServiceTypeService;
import com.motollantas.MotoLlantasVirtual.ServiceImpl.DateValidator;
import com.motollantas.MotoLlantasVirtual.dao.ServiceTypeDao;
import com.motollantas.MotoLlantasVirtual.dao.UserDao;
import com.motollantas.MotoLlantasVirtual.domain.RepairOrder;
import com.motollantas.MotoLlantasVirtual.domain.ServiceType;
import com.motollantas.MotoLlantasVirtual.domain.User;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author esteb
 */
@Controller
@RequestMapping("/garage")
public class ClientDateController {

    @Autowired
    private RepairOrderService repairOrderService;

    @Autowired
    private DateValidator dateValidator;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    ServiceTypeService serviceTypeService;

    @GetMapping("/userGarage")
    public String newClientDate(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User usuario = userDao.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        model.addAttribute("clientDateDTO", new ClientDateDTO());
        model.addAttribute("fullName", usuario.getFullName());
        model.addAttribute("identification", usuario.getIdentification());
        model.addAttribute("serviceTypes", serviceTypeService.findAll());

        return "garage/userGarage";
    }

    @PostMapping("/saveDate")
    public String saveClientDate(@ModelAttribute("clientDateDTO") ClientDateDTO clientDateDTO,
            RedirectAttributes redirectAttributes) {
        LocalDateTime appointmentDate = clientDateDTO.getAppointmentDate();

        if (appointmentDate.isBefore(LocalDateTime.now())) {
            redirectAttributes.addFlashAttribute("mensajeError", "No se puede agendar una cita en el pasado.");
            return "redirect:/garage/userGarage";
        }

        if (dateValidator.isClosedDay(appointmentDate)) {
            redirectAttributes.addFlashAttribute("mensajeError", "El taller está cerrado ese día.");
            return "redirect:/garage/userGarage";
        }

        if (!dateValidator.isWithinOpeningHours(appointmentDate)) {
            redirectAttributes.addFlashAttribute("mensajeError", "La hora seleccionada está fuera del horario de atención.");
            return "redirect:/garage/userGarage";
        }

        ServiceType serviceType = serviceTypeService.findById(clientDateDTO.getServiceTypeId());

        if (!dateValidator.isSlotAvailable(appointmentDate, serviceType)) {
            redirectAttributes.addFlashAttribute("mensajeError", "Ya existe una cita registrada en ese horario.");
            return "redirect:/garage/userGarage";
        }

        try {
            repairOrderService.createDateClient(clientDateDTO);
            redirectAttributes.addFlashAttribute("mensajeExito", "¡Cita registrada exitosamente!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError", "Error al registrar la cita: " + e.getMessage());
        }

        return "redirect:/garage/myAppointments";
    }

    @GetMapping("/myAppointments")
    public String listClientDates(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userDao.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario No Encontrado"));

        List<ClientDateDTO> clientDates
                = repairOrderService.getAppointmentbyUser(user.getIdUser());
        model.addAttribute("appointments", clientDates);
        return "garage/myAppointments";
    }

    @GetMapping("/edit/{id}")
    public String mostrarFormularioEdicion(@PathVariable Long id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User usuario = userDao.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        RepairOrder orden = repairOrderService.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));

        ClientDateDTO dto = modelMapper.map(orden, ClientDateDTO.class);

        if (dto.getAppointmentDate() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            dto.setFormattedAppointmentDate(dto.getAppointmentDate().format(formatter));
        }
        model.addAttribute("clientDateDTO", dto);
        model.addAttribute("fullName", usuario.getFullName());
        model.addAttribute("identification", usuario.getIdentification());
        model.addAttribute("serviceTypes", serviceTypeService.findAll());
        return "garage/userGarage";
    }

    @PostMapping("/updateDate")
    public String updateClientDate(@ModelAttribute("clientDateDTO") ClientDateDTO clientDateDTO,
            RedirectAttributes redirectAttributes) {
        LocalDateTime appointmentDate = clientDateDTO.getAppointmentDate();

        if (appointmentDate.isBefore(LocalDateTime.now())) {
            redirectAttributes.addFlashAttribute("mensajeError", "No se puede agendar una cita en el pasado.");
            return "redirect:/garage/myAppointments";
        }

        try {
            repairOrderService.updateDateClient(clientDateDTO);
            redirectAttributes.addFlashAttribute("mensajeExito", "¡Cita actualizada exitosamente!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError", "Error al actualizar la cita: " + e.getMessage());
        }
        return "redirect:/garage/myAppointments";
    }

    @GetMapping("/delete/{id}")
    public String eliminarCita(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            repairOrderService.deleteById(id);
            redirectAttributes.addFlashAttribute("mensajeExito", "Cita eliminada correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError", "Error al eliminar la cita: " + e.getMessage());
        }
        return "redirect:/garage/myAppointments";
    }

}
