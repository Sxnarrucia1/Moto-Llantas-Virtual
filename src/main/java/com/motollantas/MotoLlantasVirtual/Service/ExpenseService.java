/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.motollantas.MotoLlantasVirtual.Service;

import com.motollantas.MotoLlantasVirtual.domain.Expense;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author esteb
 */
public interface ExpenseService {

    List<Expense> findByDescription(String description);

    List<Expense> filterByCategory(String category);

    List<Expense> findAllActive();

    void softDelete(Long id);
    
    public BigDecimal getTotalExpense();
}
