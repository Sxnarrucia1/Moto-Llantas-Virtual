/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.motollantas.MotoLlantasVirtual.controller;

import com.motollantas.MotoLlantasVirtual.Service.FinanceDashboardService;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author esteb
 */
@Controller
@RequestMapping("/dashboard")
public class FinanceDashboardController {

    @Autowired
    private FinanceDashboardService dashboardService;

    @GetMapping("/finance")
    public String showDashboard(Model model) {
        BigDecimal income = dashboardService.getTotalIncome();
        BigDecimal expense = dashboardService.getTotalExpense();
        BigDecimal balance = dashboardService.getNetBalance();

        model.addAttribute("income", income);
        model.addAttribute("expense", expense);
        model.addAttribute("balance", balance);

        return "dashboard/finance";
    }

    @GetMapping("/api/finance-summary")
    @ResponseBody
    public Map<String, BigDecimal> getFinanceSummary() {
        Map<String, BigDecimal> summary = new HashMap<>();
        summary.put("income", dashboardService.getTotalIncome());
        summary.put("expense", dashboardService.getTotalExpense());
        summary.put("balance", dashboardService.getNetBalance());
        return summary;
    }

}
