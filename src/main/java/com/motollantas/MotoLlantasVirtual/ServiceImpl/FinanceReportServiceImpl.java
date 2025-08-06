/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.motollantas.MotoLlantasVirtual.ServiceImpl;

import com.motollantas.MotoLlantasVirtual.Service.FinanceReportService;
import com.motollantas.MotoLlantasVirtual.dao.ExpenseDao;
import com.motollantas.MotoLlantasVirtual.dao.IncomeDao;
import com.motollantas.MotoLlantasVirtual.domain.Expense;
import com.motollantas.MotoLlantasVirtual.domain.FinancialSummary;
import com.motollantas.MotoLlantasVirtual.domain.Income;
import com.motollantas.MotoLlantasVirtual.domain.ReportData;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.IsoFields;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author esteb
 */
@Service
public class FinanceReportServiceImpl implements FinanceReportService {

    @Autowired
    private IncomeDao incomeDao;

    @Autowired
    private ExpenseDao expenseDao;

    @Override
    public List<FinancialSummary> fetchRawData(LocalDate startDate, LocalDate endDate) {
        List<Income> incomes = incomeDao.findByDateBetween(startDate, endDate);
        List<Expense> expenses = expenseDao.findByDateBetween(startDate, endDate);

        Map<LocalDate, FinancialSummary> summaryMap = new TreeMap<>();

        for (Income income : incomes) {
            LocalDate date = income.getDate();
            summaryMap.computeIfAbsent(date, d -> new FinancialSummary(d, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO));
            FinancialSummary summary = summaryMap.get(date);
            summary.setIncome(summary.getIncome().add(income.getAmount()));
        }

        for (Expense expense : expenses) {
            LocalDate date = expense.getDate();
            summaryMap.computeIfAbsent(date, d -> new FinancialSummary(d, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO));
            FinancialSummary summary = summaryMap.get(date);
            summary.setExpense(summary.getExpense().add(expense.getAmount()));
        }
        summaryMap.values().forEach(s -> s.setBalance(s.getIncome().subtract(s.getExpense())));

        return new ArrayList<>(summaryMap.values());
    }

    @Override
    public ReportData generateReport(LocalDate startDate, LocalDate endDate, String period) {
        List<FinancialSummary> rawData = fetchRawData(startDate, endDate);

        Map<String, FinancialSummary> grouped = new LinkedHashMap<>();

        for (FinancialSummary entry : rawData) {
            String key = switch (period.toLowerCase()) {
                case "daily" ->
                    entry.getDate().format(DateTimeFormatter.ISO_DATE);
                case "weekly" ->
                    "Semana " + entry.getDate().get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
                case "monthly" ->
                    entry.getDate().getMonth().name() + " " + entry.getDate().getYear();
                default ->
                    "Otro";
            };

            grouped.computeIfAbsent(key, k -> new FinancialSummary(k, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO));

            FinancialSummary summary = grouped.get(key);
            summary.setIncome(summary.getIncome().add(entry.getIncome()));
            summary.setExpense(summary.getExpense().add(entry.getExpense()));
            summary.setBalance(summary.getBalance().add(entry.getBalance()));
        }

        List<FinancialSummary> summaries = new ArrayList<>(grouped.values());

        BigDecimal totalIncome = summaries.stream().map(FinancialSummary::getIncome).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalExpense = summaries.stream().map(FinancialSummary::getExpense).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal netBalance = totalIncome.subtract(totalExpense);

        return new ReportData(summaries, totalIncome, totalExpense, netBalance);
    }
}
