/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.motollantas.MotoLlantasVirtual.dao;

import com.motollantas.MotoLlantasVirtual.domain.Expense;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author esteb
 */
public interface ExpenseDao extends JpaRepository<Expense, Long> {

    List<Expense> findByCategoryAndActiveTrue(String category);

    List<Expense> findByDescriptionContainingIgnoreCaseAndActiveTrue(String description);

    List<Expense> findByCategoryContainingIgnoreCaseAndActiveTrue(String category);

    List<Expense> findByActiveTrue();

    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expense e WHERE e.active = true")
    BigDecimal sumAllActiveExpenses();

    List<Expense> findByDateBetween(LocalDate startDate, LocalDate endDate);
}
