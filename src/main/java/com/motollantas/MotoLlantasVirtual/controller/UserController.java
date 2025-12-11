package com.motollantas.MotoLlantasVirtual.controller;

import com.motollantas.MotoLlantasVirtual.Service.UserService;
import com.motollantas.MotoLlantasVirtual.domain.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;

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
    public String updateUserProfile(
            @RequestParam("fullName") String fullName,
            @RequestParam(value = "currentPassword", required = false) String currentPassword,
            @RequestParam(value = "newPassword", required = false) String newPassword,
            @RequestParam(value = "confirmNewPassword", required = false) String confirmNewPassword,
            Principal principal,
            Model model) {

        String email = principal.getName();

        String result = userService.updateUserProfile(
                email, fullName, currentPassword, newPassword, confirmNewPassword
        );

        if (result.startsWith("error:")) {
            model.addAttribute("error", result.substring(6));
        } else {
            model.addAttribute("success", result.substring(8));
        }

        model.addAttribute("user", userService.findByEmail(email));
        return "users/userProfile";
    }

    @PostMapping("/user/delete")
    public String deleteUserAccount(Principal principal, HttpServletRequest request, HttpServletResponse response) {
        
        userService.disableByEmail(principal.getName());

        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(request, response, SecurityContextHolder.getContext().getAuthentication());

        return "redirect:/login?logout";
    }

}
