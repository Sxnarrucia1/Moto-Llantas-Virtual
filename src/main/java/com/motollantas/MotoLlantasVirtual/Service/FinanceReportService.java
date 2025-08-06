/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.motollantas.MotoLlantasVirtual.Service;

import com.motollantas.MotoLlantasVirtual.domain.FinancialSummary;
import com.motollantas.MotoLlantasVirtual.domain.ReportData;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author esteb
 */
public interface FinanceReportService {
    
    public List<FinancialSummary> fetchRawData(LocalDate startDate, LocalDate endDate);
    
    public ReportData generateReport(LocalDate startDate, LocalDate endDate, String period);
}
