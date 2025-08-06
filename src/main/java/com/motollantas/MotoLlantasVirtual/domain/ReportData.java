/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.motollantas.MotoLlantasVirtual.domain;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author esteb
 */
public class ReportData {

    private List<FinancialSummary> summaries;
    private BigDecimal totalIncome;
    private BigDecimal totalExpense;
    private BigDecimal netBalance;

    public ReportData(List<FinancialSummary> summaries, BigDecimal totalIncome, BigDecimal totalExpense, BigDecimal netBalance) {
        this.summaries = summaries;
        this.totalIncome = totalIncome;
        this.totalExpense = totalExpense;
        this.netBalance = netBalance;
    }

    public List<FinancialSummary> getSummaries() {
        return summaries;
    }

    public void setSummaries(List<FinancialSummary> summaries) {
        this.summaries = summaries;
    }

    public BigDecimal getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(BigDecimal totalIncome) {
        this.totalIncome = totalIncome;
    }

    public BigDecimal getTotalExpense() {
        return totalExpense;
    }

    public void setTotalExpense(BigDecimal totalExpense) {
        this.totalExpense = totalExpense;
    }

    public BigDecimal getNetBalance() {
        return netBalance;
    }

    public void setNetBalance(BigDecimal netBalance) {
        this.netBalance = netBalance;
    }
}
