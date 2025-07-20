/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.motollantas.MotoLlantasVirtual.controller;

import com.motollantas.MotoLlantasVirtual.Service.IncomeService;
import com.motollantas.MotoLlantasVirtual.dao.IncomeDao;
import com.motollantas.MotoLlantasVirtual.domain.Income;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author esteb
 */
@Controller
@RequestMapping("/incomes")
public class IncomeController {

    @Autowired
    private IncomeDao incomeDao;

    @Autowired
    private IncomeService incomeService;

    @GetMapping("/income")
    public String listIncomes(
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String category,
            Model model) {

        List<Income> incomes;

        if (description != null && !description.isEmpty()) {
            incomes = incomeService.findByDescription(description);
        } else if (category != null && !category.isEmpty()) {
            incomes = incomeService.filterByCategory(category);
        } else {
            incomes = incomeService.findAllActive(); // Solo ingresos activos
        }

        model.addAttribute("incomes", incomes);
        model.addAttribute("categories", List.of("Mantenimiento", "Repuestos", "Servicios", "Otros"));
        model.addAttribute("income", new Income());
        return "incomes/income";
    }

    @PostMapping("/save")
    public String saveIncome(@ModelAttribute("income") @Valid Income income, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("incomes", incomeService.findAllActive());
            return "incomes/income";
        }
        incomeDao.save(income);
        redirectAttributes.addFlashAttribute("mensajeExito", "Ingreso guardado correctamente.");
        return "redirect:/incomes/income";
    }

    @GetMapping("/edit/{id}")
    public String editIncome(@PathVariable Long id, Model model) {
        model.addAttribute("income", incomeDao.findById(id).orElseThrow());
        model.addAttribute("incomes", incomeService.findAllActive());
        model.addAttribute("categories", List.of("Mantenimiento", "Repuestos", "Servicios", "Otros"));
        return "incomes/income";
    }

    @GetMapping("/delete/{id}")
    public String deleteIncome(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        incomeService.softDelete(id);
        redirectAttributes.addFlashAttribute("mensajeExito", "Ingreso eliminado correctamente.");
        return "redirect:/incomes/income";
    }
}
