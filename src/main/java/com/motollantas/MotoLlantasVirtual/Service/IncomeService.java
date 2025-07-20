/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.motollantas.MotoLlantasVirtual.Service;

import com.motollantas.MotoLlantasVirtual.domain.Income;
import java.util.List;

/**
 *
 * @author esteb
 */
public interface IncomeService {

    public List<Income> findByDescription(String description);
    
    public List<Income> filterByCategory(String category);
    
    public List<Income> findAllActive();
    
    public void softDelete(Long id);
}
