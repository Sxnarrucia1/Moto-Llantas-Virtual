package com.motollantas.MotoLlantasVirtual.controller;


import com.motollantas.MotoLlantasVirtual.Service.registerService;
import com.motollantas.MotoLlantasVirtual.dao.DocumentTypeDao;
import com.motollantas.MotoLlantasVirtual.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@CrossOrigin(origins = "*")
public class registerController {

    @Autowired
    private registerService registerService;

    @Autowired
    private DocumentTypeDao documentTypeDao;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("documentTypes", documentTypeDao.findByIsActiveTrue());
        model.addAttribute("user", new User()); // si usas th:object, mantener coherencia
        return "login/register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user,
                               RedirectAttributes ra,
                               Model model) {
        try {
            // El objeto user ya vendrá con documentType.id y identificacion bindeados
            registerService.register(user);
            ra.addFlashAttribute("success", "Usuario registrado correctamente.");
            return "redirect:/login";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            // Re-carga el catálogo para re-renderizar el select al retornar la vista
            model.addAttribute("documentTypes", documentTypeDao.findByIsActiveTrue());
            model.addAttribute("user", user);
            return "login/register";
        }
    }
}


