/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.motollantas.MotoLlantasVirtual.ServiceImpl;

import com.motollantas.MotoLlantasVirtual.Service.ExpenseService;
import com.motollantas.MotoLlantasVirtual.Service.FinanceDashboardService;
import com.motollantas.MotoLlantasVirtual.Service.IncomeService;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author esteb
 */
@Service
public class FinanceDashboardServiceImpl implements FinanceDashboardService {

    @Autowired
    private IncomeService incomeService;

    @Autowired
    private ExpenseService expenseService;

    @Override
    public BigDecimal getTotalIncome() {
        return incomeService.getTotalIncome();
    }

    @Override
    public BigDecimal getTotalExpense() {
        return expenseService.getTotalExpense();
    }

    @Override
    public BigDecimal getNetBalance() {
        return getTotalIncome().subtract(getTotalExpense());
    }

}
