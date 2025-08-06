/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.motollantas.MotoLlantasVirtual.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 *
 * @author esteb
 */
public class FinancialSummary {
    
    private LocalDate date;

    private String label;
    
    private BigDecimal income;
    
    private BigDecimal expense;
    
    private BigDecimal balance;

    public FinancialSummary(LocalDate date, BigDecimal income, BigDecimal expense, BigDecimal balance) {
        this.date = date;
        this.label = null;
        this.income = income;
        this.expense = expense;
        this.balance = balance;
    }

    public FinancialSummary(String label, BigDecimal income, BigDecimal expense, BigDecimal balance) {
        this.label = label;
        this.income = income;
        this.expense = expense;
        this.balance = balance;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    public BigDecimal getExpense() {
        return expense;
    }

    public void setExpense(BigDecimal expense) {
        this.expense = expense;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
    
}
