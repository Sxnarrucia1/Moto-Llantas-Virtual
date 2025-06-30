/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.motollantas.MotoLlantasVirtual.controller;

/**
 *
 * @author esteb
 */
import com.motollantas.MotoLlantasVirtual.Service.MotorcycleService;
import com.motollantas.MotoLlantasVirtual.Service.RepairOrderService;
import com.motollantas.MotoLlantasVirtual.Service.UserService;
import com.motollantas.MotoLlantasVirtual.domain.Motorcycle;
import com.motollantas.MotoLlantasVirtual.domain.RepairOrder;
import com.motollantas.MotoLlantasVirtual.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/motorcycle")
public class MotorcycleController {

    @Autowired
    MotorcycleService motorcycleService;

    @Autowired
    UserService userService;
    
    @Autowired
    RepairOrderService repairOrderService;

    @GetMapping("myMotorcycle")
    public String listMotorcycles(Model model, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        List<Motorcycle> motorcycles = motorcycleService.findAllByUser(user);
        model.addAttribute("motorcycles", motorcycles);
        return "motorcycle/myMotorcycle";
    }

    @GetMapping("/register")
    public String showRegisterMotorcycleForm(Model model) {
        model.addAttribute("motorcycle", new Motorcycle());
        return "motorcycle/registerMotorcycle";
    }

    @PostMapping("/register")
    public String registerMotorcycle(@ModelAttribute Motorcycle motorcycle, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        motorcycle.setUser(user);
        motorcycleService.save(motorcycle);
        return "redirect:/motorcycle/myMotorcycle";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        Motorcycle moto = motorcycleService.findById(id)
                .filter(m -> m.getUser().equals(user))
                .orElseThrow(() -> new RuntimeException("Motocicleta no encontrada o acceso denegado"));

        model.addAttribute("motorcycle", moto);
        return "motorcycle/registerMotorcycle";
    }

    @PostMapping("/update")
    public String updateMotorcycle(@ModelAttribute Motorcycle motorcycle, Principal principal, RedirectAttributes redirectAttributes) {
        User user = userService.findByEmail(principal.getName());

        Motorcycle existing = motorcycleService.findById(motorcycle.getId())
                .filter(m -> m.getUser().equals(user))
                .orElseThrow(() -> new RuntimeException("Motocicleta no encontrada o acceso denegado"));

        existing.setDisplacement(motorcycle.getDisplacement());
        existing.setKilometraje(motorcycle.getKilometraje());
        existing.setColor(motorcycle.getColor());

        motorcycleService.save(existing);
        redirectAttributes.addFlashAttribute("mensajeExito", "Motocicleta actualizada correctamente.");
        return "redirect:/motorcycle/myMotorcycle";
    }

    @PostMapping("/delete/{id}")
    public String deleteMotorcycle(@PathVariable Long id, Principal principal, RedirectAttributes redirectAttributes) {
        User user = userService.findByEmail(principal.getName());
        Motorcycle moto = motorcycleService.findById(id)
                .filter(m -> m.getUser().equals(user))
                .orElseThrow(() -> new RuntimeException("Motocicleta no encontrada o acceso denegado"));

        motorcycleService.deleteById(id);
        redirectAttributes.addFlashAttribute("mensajeExito", "Motocicleta eliminada correctamente.");
        return "redirect:/motorcycle/myMotorcycle";
    }

    @GetMapping("/services/{id}")
    public String viewServicesByMotorcycle(@PathVariable Long id, Model model, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        Motorcycle moto = motorcycleService.findById(id)
                .filter(m -> m.getUser().equals(user))
                .orElseThrow(() -> new RuntimeException("Motocicleta no encontrada o acceso denegado"));

        List<RepairOrder> orders = repairOrderService.findByMotorcycle(moto);
        model.addAttribute("motorcycle", moto);
        model.addAttribute("repairOrders", orders);
        return "motorcycle/motorcycleClientServices";
    }

}
