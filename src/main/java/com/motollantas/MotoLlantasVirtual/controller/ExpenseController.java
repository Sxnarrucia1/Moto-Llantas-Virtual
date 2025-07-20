/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.motollantas.MotoLlantasVirtual.controller;

import com.motollantas.MotoLlantasVirtual.Service.ExpenseService;
import com.motollantas.MotoLlantasVirtual.dao.ExpenseDao;
import com.motollantas.MotoLlantasVirtual.domain.Expense;
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
@RequestMapping("/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseDao expenseDao;

    @Autowired
    private ExpenseService expenseService;

    @GetMapping("/expense")
    public String listExpenses(
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String category,
            Model model) {

        List<Expense> expenses;

        if (description != null && !description.isEmpty()) {
            expenses = expenseService.findByDescription(description);
        } else if (category != null && !category.isEmpty()) {
            expenses = expenseService.filterByCategory(category);
        } else {
            expenses = expenseService.findAllActive();
        }

        model.addAttribute("expenses", expenses);
        model.addAttribute("categories", List.of("Salarios", "Compras", "Herramientas", "Otros"));
        model.addAttribute("expense", new Expense());
        return "expenses/expense";
    }

    @PostMapping("/save")
    public String saveExpense(@ModelAttribute("expense") @Valid Expense expense, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("expenses", expenseService.findAllActive());
            return "expenses/expense";
        }
        expenseDao.save(expense);
        redirectAttributes.addFlashAttribute("mensajeExito", "Egreso guardado correctamente.");
        return "redirect:/expenses/expense";
    }

    @GetMapping("/edit/{id}")
    public String editExpense(@PathVariable Long id, Model model) {
        model.addAttribute("expense", expenseDao.findById(id).orElseThrow());
        model.addAttribute("expenses", expenseService.findAllActive());
        model.addAttribute("categories", List.of("Mantenimiento", "Repuestos", "Servicios", "Otros"));
        return "expenses/expense";
    }

    @GetMapping("/delete/{id}")
    public String deleteExpense(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        expenseService.softDelete(id);
        redirectAttributes.addFlashAttribute("mensajeExito", "Egreso eliminado correctamente.");
        return "redirect:/expenses/expense";
    }
}
