/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.motollantas.MotoLlantasVirtual.Service;

import java.math.BigDecimal;

/**
 *
 * @author esteb
 */
public interface FinanceDashboardService {

    public BigDecimal getTotalIncome();
    
    public BigDecimal getTotalExpense();
    
    public BigDecimal getNetBalance();
}
