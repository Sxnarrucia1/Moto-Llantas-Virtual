/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.motollantas.MotoLlantasVirtual.ServiceImpl;

import com.motollantas.MotoLlantasVirtual.Service.IncomeService;
import com.motollantas.MotoLlantasVirtual.dao.IncomeDao;
import com.motollantas.MotoLlantasVirtual.domain.Income;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author esteb
 */
@Service
public class IncomeServiceImpl implements IncomeService {

    @Autowired
    private IncomeDao incomeDao;

    @Override
    public List<Income> findByDescription(String description) {
        return incomeDao.findByDescriptionContainingIgnoreCaseAndActiveTrue(description);
    }

    @Override
    public List<Income> filterByCategory(String category) {
        return incomeDao.findByCategoryContainingIgnoreCaseAndActiveTrue(category);
    }

    @Override
    public List<Income> findAllActive() {
        return incomeDao.findByActiveTrue();
    }

    @Override
    public void softDelete(Long id) {
        Income income = incomeDao.findById(id).orElseThrow();
        income.setActive(false);
        incomeDao.save(income);
    }

    @Override
    public BigDecimal getTotalIncome() {
        return incomeDao.sumAllActiveIncomes();
    }
}
