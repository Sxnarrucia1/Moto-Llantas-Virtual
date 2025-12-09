package com.motollantas.MotoLlantasVirtual.controller;

import com.motollantas.MotoLlantasVirtual.Service.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PasswordResetController {

    @Autowired
    private PasswordResetService resetService;

    @GetMapping("/forgotPassword")
    public String forgotPasswordPage() {
        return "login/forgotPassword";
    }

    @PostMapping("/forgotPassword")
    public String sendResetEmail(@RequestParam String email, Model model) {
        try {
            resetService.sendResetToken(email);
            model.addAttribute("success", "Correo enviado, revisa tu bandeja de entrada.");
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
        }
        return "login/forgotPassword";
    }

    @GetMapping("/resetPassword")
    public String resetPasswordPage(@RequestParam String token, Model model) {
        if (!resetService.validateToken(token)) {
            model.addAttribute("error", "Token inválido o expirado.");
            return "login/login";
        }
        model.addAttribute("token", token);
        return "login/resetPassword";
    }

    @PostMapping("/resetPassword")
    public String resetPassword(@RequestParam String token,
                                @RequestParam String password,
                                Model model) {
        try {
            resetService.resetPassword(token, password);
            model.addAttribute("success", "Contraseña cambiada correctamente. Ahora puedes iniciar sesión.");
            return "login/login";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("token", token);
            return "login/resetPassword";
        }
    }
}
