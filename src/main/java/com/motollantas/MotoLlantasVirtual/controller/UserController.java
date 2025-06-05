package com.motollantas.MotoLlantasVirtual.controller;

import com.motollantas.MotoLlantasVirtual.Service.UserService;
import com.motollantas.MotoLlantasVirtual.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/userProfile")
    public String showProfile(Model model) {
        User user = userService.getCurrentUser();
        model.addAttribute("user", user);
        return "users/userProfile"; //
    }


    @PostMapping("/user/update")
    public String updateUserProfile(@RequestParam("fullName") String fullName,
                                    @RequestParam("currentPassword") String currentPassword,
                                    @RequestParam("newPassword") String newPassword,
                                    @RequestParam("confirmNewPassword") String confirmNewPassword,
                                    Principal principal,
                                    Model model) {

        // Buscar el usuario autenticado
        User user = userService.findByEmail(principal.getName());

        // Validar contraseña actual
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            model.addAttribute("error", "La contraseña actual es incorrecta.");
            model.addAttribute("user", user);
            return "profile";
        }

        // Validar coincidencia entre nueva contraseña y confirmación
        if (!newPassword.equals(confirmNewPassword)) {
            model.addAttribute("error", "Las nuevas contraseñas no coinciden.");
            model.addAttribute("user", user);
            return "profile";
        }

        // Validar fuerza de la contraseña
        if (!isPasswordStrong(newPassword)) {
            model.addAttribute("error", "La nueva contraseña no cumple con los requisitos.");
            model.addAttribute("user", user);
            return "profile";
        }

        // Actualizar nombre y contraseña
        user.setFullName(fullName);
        user.setPassword(passwordEncoder.encode(newPassword));
        userService.save(user);

        model.addAttribute("success", "Perfil actualizado correctamente.");
        model.addAttribute("user", user);
        return "profile";
    }

    @DeleteMapping("/user/delete")
    public String deleteUserAccount(Principal principal) {
        userService.deleteByEmail(principal.getName());
        return "redirect:/logout";
    }

    private boolean isPasswordStrong(String password) {
        return password != null && password.length() >= 8;
    }





}
