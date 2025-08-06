/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.motollantas.MotoLlantasVirtual.ServiceImpl;

import com.motollantas.MotoLlantasVirtual.Service.ExpenseService;
import com.motollantas.MotoLlantasVirtual.dao.ExpenseDao;
import com.motollantas.MotoLlantasVirtual.domain.Expense;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author esteb
 */
@Service
public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    private ExpenseDao expenseDao;

    @Override
    public List<Expense> findByDescription(String description) {
        return expenseDao.findByDescriptionContainingIgnoreCaseAndActiveTrue(description);
    }

    @Override
    public List<Expense> filterByCategory(String category) {
        return expenseDao.findByCategoryContainingIgnoreCaseAndActiveTrue(category);
    }

    @Override
    public List<Expense> findAllActive() {
        return expenseDao.findByActiveTrue();
    }

    @Override
    public void softDelete(Long id) {
        Expense expense = expenseDao.findById(id).orElseThrow();
        expense.setActive(false);
        expenseDao.save(expense);
    }

    @Override
    public BigDecimal getTotalExpense() {
        return expenseDao.sumAllActiveExpenses();
    }
}
