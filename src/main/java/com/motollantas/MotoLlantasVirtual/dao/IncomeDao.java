/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.motollantas.MotoLlantasVirtual.dao;

import com.motollantas.MotoLlantasVirtual.domain.Income;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author esteb
 */
public interface IncomeDao extends JpaRepository<Income, Long> {

    List<Income> findByCategoryAndActiveTrue(String category);

    List<Income> findByDescriptionContainingIgnoreCaseAndActiveTrue(String description);

    List<Income> findByCategoryContainingIgnoreCaseAndActiveTrue(String category);

    List<Income> findByActiveTrue();

    @Query("SELECT COALESCE(SUM(i.amount), 0) FROM Income i WHERE i.active = true")
    BigDecimal sumAllActiveIncomes();

}
