package com.motollantas.MotoLlantasVirtual.controller;

import com.motollantas.MotoLlantasVirtual.Service.UserService;
import com.motollantas.MotoLlantasVirtual.domain.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
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
                                    @RequestParam(value = "currentPassword", required = false) String currentPassword,
                                    @RequestParam(value = "newPassword", required = false) String newPassword,
                                    @RequestParam(value = "confirmNewPassword", required = false) String confirmNewPassword,
                                    Principal principal,
                                    Model model) {

        User user = userService.findByEmail(principal.getName());

        // Actualizar nombre si es necesario
        user.setFullName(fullName);

        // Si los campos de contraseña están presentes, se intenta cambiar la contraseña
        if (currentPassword != null && !currentPassword.isBlank() &&
                newPassword != null && !newPassword.isBlank() &&
                confirmNewPassword != null && !confirmNewPassword.isBlank()) {

            // Valida contraseña actual
            if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
                model.addAttribute("error", "La contraseña actual no es correcta.");
                model.addAttribute("user", user);
                return "users/userProfile";
            }

            // Valida que la nueva y la confirmación coincidan
            if (!newPassword.equals(confirmNewPassword)) {
                model.addAttribute("error", "La nueva contraseña y su confirmación no coinciden.");
                model.addAttribute("user", user);
                return "users/userProfile";
            }


            user.setPassword(passwordEncoder.encode(newPassword));
            model.addAttribute("success", "Contraseña actualizada correctamente.");
        } else {
            model.addAttribute("success", "Perfil actualizado correctamente.");
        }

        userService.save(user);
        model.addAttribute("user", user);
        return "users/userProfile";
    }




    @PostMapping("/user/delete")
    public String deleteUserAccount(Principal principal, HttpServletRequest request, HttpServletResponse response) {
        userService.deleteByEmail(principal.getName());


        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(request, response, SecurityContextHolder.getContext().getAuthentication());


        return "redirect:/login?logout";
    }


}
