/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.motollantas.MotoLlantasVirtual.ServiceImpl;

import com.motollantas.MotoLlantasVirtual.Service.ServiceTypeService;
import com.motollantas.MotoLlantasVirtual.dao.ServiceTypeDao;
import com.motollantas.MotoLlantasVirtual.domain.ServiceType;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author esteb
 */
@Service
public class ServiceTypeServiceImpl implements ServiceTypeService {

    @Autowired
    ServiceTypeDao serviceTypeD;

    @Override
    public ServiceType findById(Long id) {
        return serviceTypeD.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tipo de servicio no encontrado"));
    }

    @Override
    public List<ServiceType> findAll() {
        return serviceTypeD.findAll();
    }

}
