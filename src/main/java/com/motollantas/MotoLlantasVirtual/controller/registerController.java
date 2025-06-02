package com.motollantas.MotoLlantasVirtual.controller;


import com.motollantas.MotoLlantasVirtual.domain.User;
import com.motollantas.MotoLlantasVirtual.Service.registerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class registerController {

    @Autowired
    private registerService registerService;

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model) {
        try {
            registerService.register(user);
            return "redirect:/login";
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "redirect:/login/register";
        }
    }
}

