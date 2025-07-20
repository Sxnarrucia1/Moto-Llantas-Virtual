/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.motollantas.MotoLlantasVirtual.dao;

import com.motollantas.MotoLlantasVirtual.domain.Expense;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author esteb
 */
public interface ExpenseDao extends JpaRepository<Expense, Long> {

    List<Expense> findByCategoryAndActiveTrue(String category);

    List<Expense> findByDescriptionContainingIgnoreCaseAndActiveTrue(String description);

    List<Expense> findByCategoryContainingIgnoreCaseAndActiveTrue(String category);

    List<Expense> findByActiveTrue();
}
