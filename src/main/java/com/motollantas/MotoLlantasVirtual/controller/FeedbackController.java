/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.motollantas.MotoLlantasVirtual.controller;

import com.motollantas.MotoLlantasVirtual.Service.FeedbackService;
import com.motollantas.MotoLlantasVirtual.dao.RepairOrderDao;
import com.motollantas.MotoLlantasVirtual.domain.Feedback;
import com.motollantas.MotoLlantasVirtual.domain.RepairOrder;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author esteb
 */
@Controller
@RequestMapping("/feedbacks")
public class FeedbackController {

    @Autowired
    FeedbackService feedbackService;
    
    @Autowired
    RepairOrderDao repairOrderDao;

    @GetMapping("/feedback/{repairOrderId}")
    public String showFeedbackForm(@PathVariable Long repairOrderId,
            Model model,
            RedirectAttributes redirectAttributes) {
        RepairOrder order = repairOrderDao.findById(repairOrderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (feedbackService.existsByRepairOrderId(repairOrderId)) {
            redirectAttributes.addFlashAttribute("mensajeError", "Ya has enviado un feedback para esta orden.");
            return "redirect:/garage/myAppointments";
        }

        model.addAttribute("repairOrder", order);
        model.addAttribute("feedback", new Feedback());
        return "feedbacks/feedback";
    }

    @PostMapping("/feedback/{repairOrderId}")
    public String submitFeedback(@PathVariable Long repairOrderId,
            @ModelAttribute Feedback feedback,
            RedirectAttributes redirectAttributes) {
        RepairOrder order = repairOrderDao.findById(repairOrderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (feedbackService.existsByRepairOrderId(repairOrderId)) {
            redirectAttributes.addFlashAttribute("mensajeError", "Ya se ha registrado un feedback para esta orden.");
            return "redirect:/garage/myAppointments";
        }

        feedback.setRepairOrder(order);
        feedback.setSubmittedAt(LocalDateTime.now());
        feedbackService.saveFeedback(feedback);

        redirectAttributes.addFlashAttribute("mensajeExito", "¡Gracias por calificar el servicio!");
        return "redirect:/garage/myAppointments";
    }
}
