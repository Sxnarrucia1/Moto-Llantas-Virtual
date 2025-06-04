/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.motollantas.MotoLlantasVirtual.Service;

import com.motollantas.MotoLlantasVirtual.domain.ServiceType;
import java.util.List;

/**
 *
 * @author esteb
 */

public interface ServiceTypeService {
    
    public ServiceType findById(Long id);
    
    public List<ServiceType> findAll();
}
