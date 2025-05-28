package com.motollantas.MotoLlantasVirtual.Controller;


import com.motollantas.MotoLlantasVirtual.Domain.User;
import com.motollantas.MotoLlantasVirtual.Service.registerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class registerController {

    @Autowired
    private registerService registerService;

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user) {
        registerService.register(user);
        return "redirect:/login";
    }
}
